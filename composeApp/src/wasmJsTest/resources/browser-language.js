Object.defineProperty(Navigator.prototype, "languages", {
  configurable: true,
  get: () => ["en-US"],
});
Object.defineProperty(Navigator.prototype, "language", { configurable: true, get: () => "en-US" });
