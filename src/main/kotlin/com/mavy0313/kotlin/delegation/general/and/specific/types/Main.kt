package com.mavy0313.kotlin.delegation.general.and.specific.types

//class Cow {
//    fun eat() = println("Eating grass - munch, munch, munch!")
//}
//
//class Chicken {
//    fun eat() = println("Eating bugs - munch, munch, munch!")
//}
//
//class Pig {
//    fun eat() = println("Eating corn - munch, munch, munch!")
//}

interface Eater {
    fun eat()
}

class Muncher(private val food: String) : Eater {
    override fun eat() = println("Eating $food - munch, munch, munch!")
}

class Cow : Eater by Muncher("grass")
class Chicken : Eater by Muncher("bugs")
//class Pig : Eater by Muncher("corn")

//class Pig : Eater {
//    override fun eat() = println("Scarfing down corn - NOM NOM NOM!!!")
//}

class Scarfer(private val food: String) : Eater {
    override fun eat() = println("Scarfing down $food - NOM NOM NOM!!!")
}

class Pig : Eater by Scarfer("corn")

fun main() {
    Cow().eat()     // Eating grass - munch, munch, munch!
    Chicken().eat() // Eating bugs - munch, munch, munch!
//    Pig().eat()     // Eating corn - munch, munch, munch!
    Pig().eat()     // Scarfing down corn - NOM NOM NOM!!!
}

