// ServiceWorkerの登録をする
if ('serviceWorker' in navigator) {
    navigator.serviceWorker
        .register('sw.js', {scope: '/what-is-the-date-today/'})
        .then(() => { console.log('Service Worker Registered'); });
}


function main() {
    const date = document.getElementById("date");
    date.textContent = getDate();
}


mainLoaded = true;
if (mainLoaded == wasmLoaded) {
    main();
}
