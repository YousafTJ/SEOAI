fetch("https://catfact.ninja/fact")
    .then(response =>{
        return response.json();
    })
    .then(dataAsJSON =>{
        console.log(dataAsJSON);
    });