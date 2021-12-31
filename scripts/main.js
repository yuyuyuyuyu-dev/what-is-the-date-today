function runButtonOnClicked() {
    console.log(getDate());
}


function main() {
    const date = document.getElementById("date");
    date.textContent = getDate();
}


mainLoaded = true;
if (mainLoaded == wasmLoaded) {
    main();
}
