package com.mavy0313.kotlin.generics

//enum class Coffee { LIGHT_ROAST, MEDIUM_ROAST, DARK_ROAST }
//class Mug(val beverage: Coffee)

fun drink(coffee: Coffee) = println("Drinking coffee: $coffee")

//enum class Tea { GREEN_TEA, BLACK_TEA, RED_TEA }
fun drink(tea: Tea) = println("Drinking tea: $tea")

//class CoffeeMug(val coffee: Coffee)
//class TeaMug(val tea: Tea)

sealed interface Beverage
enum class Tea : Beverage { GREEN_TEA, BLACK_TEA, RED_TEA }
enum class Coffee : Beverage { LIGHT_ROAST, MEDIUM_ROAST, DARK_ROAST }

//class Mug(val beverage: Beverage)
class Mug<T> (val beverage: T)

fun main() {
//    val mug = Mug(Coffee.LIGHT_ROAST)
//    drink(mug.beverage)

    val mugOfCoffee = Mug(Coffee.LIGHT_ROAST)
    val mugOfTea = Mug(Tea.BLACK_TEA)

    drink(mugOfCoffee.beverage)
    drink(mugOfTea.beverage)
}