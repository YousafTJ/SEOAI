const pokemon = {
    name:"Pikachu",
    type: "Electric",
    generation: 1,
    hasEvolution: true,
    makeSound: function(){
        console.log("Pika pikachu");
    }
}

//Usage of the object
console.log(pokemon.name);
console.log(pokemon.generation);
console.log(pokemon.hasEvolution);

pokemon.makeSound();

//Another object that looks like it
const anotherPokemon = {
    name:"Snorlax",
    type: "Normal",
    age: 26,
    hasEvolution: true,
    makeSound: function(){
        console.log("zzzzz 😴");
    }
}
