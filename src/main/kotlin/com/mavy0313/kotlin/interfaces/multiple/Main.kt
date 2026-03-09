package com.mavy0313.kotlin.interfaces.multiple

class Chicken(override val name: String, var numberOfEggs: Int = 0) : Named, Speaker {
    override fun speak() = println("Cluck!")
}
class Pig(override val name: String, val excitementLevel: Int) : Named, Speaker {
    override fun speak() {
        repeat(excitementLevel) {
            println("Oink!")
        }
    }
}

class Cow(override val name: String) : FarmAnimal {
    override fun speak() = println("Moo!")
}

interface FarmAnimal : Speaker, Named

interface Speaker {
    fun speak() {
        println("...")
    }
}

interface Named {
    val name: String get() = "No name"
}

class Snail(override val name: String) : FarmAnimal

class UnknownAnimal : FarmAnimal

class Farmer(override val name: String) : Named {
    fun greet(animal: FarmAnimal) {
        println("Good morning, ${animal.name}!")
        animal.speak()
    }
}

fun main() {
//    val sue = Farmer("Sue")
//    val henrietta = Chicken("Henrietta")
//    val hamlet = Pig("Hamlet", 1)
//    val dairyGodmother = Cow("Dairy Godmother")
//
//    sue.greet(henrietta)
//    sue.greet(hamlet)
//    sue.greet(dairyGodmother)
    val roster: List<Named> = listOf(
        Farmer("Sue"),
        Chicken("Henrietta"),
        Pig("Hamlet", 1),
        Cow("Dairy Godmother")
    )

    val snail = Snail("Slick")
    snail.speak()

    val unknown = UnknownAnimal()
}