fetch('https://cat-fact.herokuapp.com/facts/random')
    .then(data => {

        return data.json();
    })
    .then(post => {
        console.log("asdads");
    });