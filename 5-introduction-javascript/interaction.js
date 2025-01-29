
let count = 0;

document.querySelector("#button").addEventListener("click", ()=>{
    const square = document.querySelector("#square");
    const color = window.getComputedStyle(square).backgroundColor;

    if(count > 9){
        square.style.visibility = "hidden";
    }

    if(color === "rgb(165, 42, 42)"){
        square.style.backgroundColor = "blue"
    }
    else{
        square.style.backgroundColor = "brown"
    }
    count++;
})