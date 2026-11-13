import { mkdir, writeFile } from "node:fs/promises";
import { join } from "node:path";
import { chromium, expect, type Page, test } from "@playwright/test";

for (const { name, language } of [
	{ name: "a Japanese", language: "ja" },
	{ name: "an English", language: "en" },
]) {
	test(`should not offer to translate the page in ${name} browser`, async ({
		baseURL,
	}, testInfo) => {
		// Arrange
		const profile = testInfo.outputPath("profile");
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
		const translateInternals = await browser.newPage();
		await translateInternals.goto("chrome://translate-internals/");
		const app = await browser.newPage();

		// Act
		await app.goto(baseURL ?? "/");
		await expect(app.locator("html")).toHaveAttribute("lang", language, {
			timeout: 30_000,
		});

		// Assert
		await expect
			.poll(() => translationOffers(translateInternals, app.url()))
			.toEqual(["false"]);
		await browser.close();
	});
}

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
