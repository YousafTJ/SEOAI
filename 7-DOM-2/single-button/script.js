'use strict'

const button = document.querySelector("#spinButton");
let timesPressed = 0;

//Add event listener to the test button 

button.addEventListener("click", () => {
    //This spins around the button
    document.getElementById("spinButton").style.transform += "rotate(360deg)";
});
