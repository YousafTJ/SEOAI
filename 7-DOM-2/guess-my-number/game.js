//Save the buttons as constants
//Input
const inputField = document.querySelector("#user-guess");

//Buttons
const guessButton = document.querySelector("#guess-button");

//Add an eventListener that takes input from the input field
guessButton.addEventListener("click", ()=>{
    console.log(inputField.value);
});


//Print something to the console

//Make a random number between 1-5
let randomNumber = generateNumber();
console.log(`The random number is ${randomNumber}`);

//Returns a random number between 1-5
function generateNumber(){
    return Math.floor(Math.random() * 5) + 1;
}