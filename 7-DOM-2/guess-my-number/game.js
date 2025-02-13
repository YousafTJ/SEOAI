//Save the buttons as constants

//Add an eventListener that takes input from the input field
//Print something to the console

//Make a random number between 1-5
let randomNumber = generateNumber();
console.log(randomNumber);

//Returns a random number between 1-5
function generateNumber(){
    return Math.floor(Math.random() * 5) + 1;
}