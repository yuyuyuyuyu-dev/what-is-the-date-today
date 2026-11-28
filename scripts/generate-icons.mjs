import { readFile, writeFile } from "node:fs/promises";
import { chromium } from "@playwright/test";

const resources = new URL(
	"../composeApp/src/webMain/resources/",
	import.meta.url,
);
const source = await readFile(new URL("icons/icon.svg", resources), "utf8");

const appIconSizes = [72, 96, 128, 144, 152, 192, 384, 512];
const faviconSizes = [16, 24, 32, 48, 64];
const faviconViewBox = "66 60 380 380";
const faviconCornerRadius = "22%";

const browser = await chromium.launch();
const page = await browser.newPage();

const render = async (size, { viewBox, cornerRadius }) => {
	await page.setViewportSize({ width: size, height: size });
	await page.setContent(`<body style="margin:0">${source}</body>`);
	const svg = page.locator("svg");
	await svg.evaluate(
		(element, { size, viewBox, cornerRadius }) => {
			element.setAttribute("width", size);
			element.setAttribute("height", size);
			if (viewBox) element.setAttribute("viewBox", viewBox);
			element.style.display = "block";
			if (cornerRadius)
				element.style.clipPath = `inset(0 round ${cornerRadius})`;
		},
		{ size, viewBox, cornerRadius },
	);
	return svg.screenshot({ omitBackground: true });
};

for (const size of appIconSizes) {
	await writeFile(
		new URL(`icons/icon-${size}x${size}.png`, resources),
		await render(size, {}),
	);
}

const favicons = [];
for (const size of faviconSizes) {
	favicons.push(
		await render(size, {
			viewBox: faviconViewBox,
			cornerRadius: faviconCornerRadius,
		}),
	);
}

await browser.close();

const header = Buffer.alloc(6);
header.writeUInt16LE(1, 2);
header.writeUInt16LE(favicons.length, 4);

let offset = header.length + 16 * favicons.length;
const entries = favicons.map((png, index) => {
	const entry = Buffer.alloc(16);
	entry.writeUInt8(faviconSizes[index], 0);
	entry.writeUInt8(faviconSizes[index], 1);
	entry.writeUInt16LE(1, 4);
	entry.writeUInt16LE(32, 6);
	entry.writeUInt32LE(png.length, 8);
	entry.writeUInt32LE(offset, 12);
	offset += png.length;
	return entry;
});

await writeFile(
	new URL("favicon.ico", resources),
	Buffer.concat([header, ...entries, ...favicons]),
);
