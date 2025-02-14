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
// console.log(newArray);

//Exercise D
const products = [
    { name: "Keyboard", category: "Electronics", price: 29.99 },
    { name: "T-Shirt",  category: "Clothing",    price: 9.99  },
    { name: "Mug",      category: "Kitchen",     price: 5.0   },
    { name: "TV",       category: "Electronics", price: 299.99},
    { name: "Jeans",    category: "Clothing",    price: 39.99 },
    { name: "Blender",  category: "Kitchen",     price: 45.0  },
];

products.forEach(productCallback)

function productCallback(elem){
    console.log(elem);
}