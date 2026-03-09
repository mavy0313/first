package com.mavy0313.kotlin.generics.type.parameter.constraints


fun drink(coffee: Coffee) = println("Drinking coffee: $coffee")

fun drink(tea: Tea) = println("Drinking tea: $tea")

sealed interface Beverage {
    val idealTemperature: Int
}
enum class Tea : Beverage {
    GREEN_TEA, BLACK_TEA, RED_TEA;
    override val idealTemperature = 140
}
enum class Coffee : Beverage {
    LIGHT_ROAST, MEDIUM_ROAST, DARK_ROAST;
    override val idealTemperature = 135
}

//class Mug(val beverage: Beverage)
class Mug<T : Beverage> (val beverage: T) {
    val temperature = beverage.idealTemperature
}

fun main() {
//    val mug = Mug(Coffee.LIGHT_ROAST)
//    drink(mug.beverage)

    val mugOfCoffee = Mug(Coffee.LIGHT_ROAST)
    val mugOfTea = Mug(Tea.BLACK_TEA)

    drink(mugOfCoffee.beverage)
    drink(mugOfTea.beverage)

//    val mugOfString: Mug<String> = Mug("How did this get in the mug?")
//    val mugOfInt: Mug<Int> = Mug(5)
//    val mugOfBoolean: Mug<Boolean> = Mug(true)
//    val mugOfEmptiness: Mug<Any?> = Mug(null)
}