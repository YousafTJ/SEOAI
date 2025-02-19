//Første opgave
document.querySelector(".close-modal").addEventListener("click", () =>{
    document.querySelector(".modal").style.display = "none";
});

let all = document.querySelectorAll("button");
let first = all[1];
first.addEventListener("click",(event)=>{
    console.log(event.target.parentNode);
})

