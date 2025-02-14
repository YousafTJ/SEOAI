//querySelector Fetch the first image
let htmlElement = document.querySelector("img");
console.log(htmlElement);

//querySelectorAll Fetch all images
let allHtmlElements = document.querySelectorAll("img");
console.log(allHtmlElements)

//Delete all images
// allHtmlElements.forEach(callBack);
function callBack(HTMLElement){
    HTMLElement.remove();
}

document.querySelector("button").addEventListener("click", getId);

//This can be used in "Event driven programming" exercise E. This function fetches the ID of an image.
function getId(event) {
    console.log(event.target.classList);
}