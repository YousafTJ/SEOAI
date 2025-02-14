//Array of numbers
const numbers = [false, "asdljh",true, 1, 312, 2, 1243, 45, 99, 231, 4, 5, 5, 5];

//Callback function
function callBack(fisk){
    // console.log(fisk);
}

//Foreach loop
numbers.forEach(callBack);

//Callback function
function callbackHigher(index){
    if(index > 0){
        return true;
    }
    else{
        return false
    }
}

//Filter
const newArray = numbers.filter(callbackHigher);
console.log(newArray);