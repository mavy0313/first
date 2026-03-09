package com.mavy0313.kotlin.interfaces

class Chicken(override val name: String, var numberOfEggs: Int = 0) : FarmAnimal {
    override fun speak() = println("Cluck!")
}
class Pig(override val name: String, val excitementLevel: Int) : FarmAnimal {
    override fun speak() {
        repeat(excitementLevel) {
            println("Oink!")
        }
    }
}

class Cow(override val name: String) : FarmAnimal {
    override fun speak() = println("Moo!")
}

interface FarmAnimal {
    val name: String
    fun speak()
}

class Farmer(val name: String) {
//    fun greet(chicken: Chicken) {
//        println("Good morning, ${chicken.name}!")
//        chicken.speak()
//    }
//    fun greet(pig: Pig) {
//        println("Good morning, ${pig.name}!")
//        pig.speak()
//    }
//    fun greet(cow: Cow) {
//        println("Good morning, ${cow.name}!")
//        cow.speak()
//    }
    fun greet(animal: FarmAnimal) {
        println("Good morning, ${animal.name}!")
        animal.speak()
    }

//    fun greet2(animal: FarmAnimal) {
//        println("Hello, ${animal.name}!")
//        println("I see you have ${animal.numberOfEggs} eggs today!")
//        animal.speak()
//    }
    //smart cast
    fun greet2(animal: FarmAnimal) {
        println("Hello, ${animal.name}!")
        if (animal is Chicken) {
            println("I see you have ${animal.numberOfEggs} eggs today!")
        }
        animal.speak()
    }
    //unsafe cast
    fun greet3(animal: FarmAnimal) {
        println("Hello, ${animal.name}!")
        val chicken: Chicken = animal as Chicken
        println("I see you have ${chicken.numberOfEggs} eggs today!")
        animal.speak()
    }
    //safe cast
    fun greet4(animal: FarmAnimal) {
        println("Hello, ${animal.name}!")

        val chicken: Chicken? = animal as? Chicken
        chicken?.let { println("I see you have ${it.numberOfEggs} eggs today!") }

        animal.speak()
    }

}

fun main() {
    val sue = Farmer("Sue")
    val henrietta = Chicken("Henrietta")
    val hamlet = Pig("Hamlet", 1)
    val dairyGodmother = Cow("Dairy Godmother")

    sue.greet(henrietta)
    sue.greet(hamlet)
    sue.greet(dairyGodmother)
}