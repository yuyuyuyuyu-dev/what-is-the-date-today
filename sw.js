self.addEventListener('install', (e) => {
  e.waitUntil(
    caches.open('what-is-the-date-today').then((cache) => cache.addAll([
        '/what-is-the-date-today/',
        '/what-is-the-date-today/index.html',
        '/what-is-the-date-today/styles/style.css',
        '/what-is-the-date-today/scripts/main.js',
        '/what-is-the-date-today/scripts/wasm_exec.js',
        '/what-is-the-date-today/go/main.wasm',
    ])),
  );
});


self.addEventListener('fetch', function (event) {});
