const composeResources = `${config.basePath}/kotlin/composeResources`;

config.files.push({
  pattern: `${composeResources}/**/*`,
  included: false,
  served: true,
  watched: false,
});
config.proxies["/composeResources/"] = `/absolute${composeResources}/`;
config.client.mocha = { ...config.client.mocha, timeout: 30000 };
