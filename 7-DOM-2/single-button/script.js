'use strict'

const button = document.querySelector("#spinButton");

const testButton = document.querySelector("#test");

testButton.addEventListener("click", () =>{
    console.log("Hey du klikkede knappen!!!!");
});

let timesPressed = 0;

//Add event listener to the test button 

button.addEventListener("click", () => {
    //This spins around the button
    document.getElementById("spinButton").style.transform += "skew(-50deg, 25deg) rotate(270deg)";
});
