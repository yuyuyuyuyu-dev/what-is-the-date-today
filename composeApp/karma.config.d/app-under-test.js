const composeResources = `${config.basePath}/kotlin/composeResources`;

config.files.push({
  pattern: `${composeResources}/**/*`,
  included: false,
  served: true,
  watched: false,
});
config.proxies["/composeResources/"] = `/absolute${composeResources}/`;
config.files.unshift({
  pattern: `${config.basePath}/kotlin/browser-language.js`,
  included: true,
  served: true,
  watched: false,
});
process.env.TZ = "Asia/Tokyo";
config.client.mocha = { ...config.client.mocha, timeout: 30000 };
