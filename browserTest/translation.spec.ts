import { mkdir, writeFile } from "node:fs/promises";
import { join } from "node:path";
import {
	type BrowserContext,
	chromium,
	expect,
	type Page,
	test,
} from "@playwright/test";

test("should not offer to translate the page in a browser of any language", async ({
	baseURL,
}, testInfo) => {
	test.setTimeout(15 * 60_000);

	// Arrange
	const languages = await withChrome(
		testInfo.outputPath("profile"),
		"en",
		(translateInternals) => supportedLanguages(translateInternals),
	);
	expect(languages).toContain("ja");
	expect(languages).toContain("en");

	// Act
	const offers = await mapConcurrently(languages, 8, (language) =>
		withChrome(
			testInfo.outputPath(`profile-${language}`),
			language,
			async (translateInternals, browser) => {
				const app = await browser.newPage();
				await app.goto(baseURL ?? "/");
				await expect(app.locator("html")).toHaveAttribute(
					"lang",
					language === "ja" ? "ja" : "en",
					{ timeout: 60_000 },
				);
				await expect
					.poll(() => translationOffers(translateInternals, app.url()))
					.toHaveLength(1);
				return [
					language,
					(await translationOffers(translateInternals, app.url()))[0],
				];
			},
		),
	);

	// Assert
	expect(Object.fromEntries(offers)).toEqual(
		Object.fromEntries(languages.map((language) => [language, "false"])),
	);
});

const withChrome = async <T>(
	profile: string,
	language: string,
	use: (translateInternals: Page, browser: BrowserContext) => Promise<T>,
) => {
	await mkdir(join(profile, "Default"), { recursive: true });
	await writeFile(
		join(profile, "Default", "Preferences"),
		JSON.stringify({
			intl: { accept_languages: language },
			translate_recent_target: language,
			translate_blocked_languages: [language],
		}),
	);
	const browser = await chromium.launchPersistentContext(profile, {
		channel: "chrome",
		locale: language,
		args: [`--lang=${language}`],
	});
	try {
		const translateInternals = await browser.newPage();
		await translateInternals.goto("chrome://translate-internals/");
		return await use(translateInternals, browser);
	} finally {
		await browser.close();
	}
};

const mapConcurrently = async <T, R>(
	items: T[],
	concurrency: number,
	map: (item: T) => Promise<R>,
) => {
	const results: R[] = new Array(items.length);
	let next = 0;
	await Promise.all(
		Array.from({ length: concurrency }, async () => {
			while (next < items.length) {
				const index = next++;
				results[index] = await map(items[index]);
			}
		}),
	);
	return results;
};

const supportedLanguages = (translateInternals: Page) =>
	translateInternals.evaluate(() =>
		[...document.querySelectorAll("#prefs-supported-languages li")]
			.map((item) => item.textContent?.trim().split(" ")[0] ?? "")
			.filter((code) => code !== ""),
	);

const translationOffers = (translateInternals: Page, url: string) =>
	translateInternals.evaluate((url) => {
		const table = [...document.querySelectorAll("table")].find((table) =>
			[...table.querySelectorAll("th")].some(
				(header) => header.textContent?.trim() === "UI shown",
			),
		);
		if (!table) return [];
		const headers = [...table.querySelectorAll("th")].map((header) =>
			header.textContent?.trim(),
		);
		const urlColumn = headers.indexOf("URL");
		const uiShownColumn = headers.indexOf("UI shown");
		return [...table.querySelectorAll("tr")]
			.map((row) => [...row.querySelectorAll("td")])
			.filter((cells) => cells[urlColumn]?.textContent?.trim() === url)
			.map((cells) => cells[uiShownColumn]?.textContent?.trim());
	}, url);
