package com.mavy0313.kotlin.delegation.withinterface


interface KitchenStaff {
    val specials: List<String>
    fun prepareEntree(name: String): Entree?
    fun prepareAppetizer(name: String): Appetizer?
    fun prepareDessert(name: String): Dessert?
    fun receiveCompliment(message: String)
}

class Dessert {

}

class Appetizer {

}

class Chef : KitchenStaff {
    override fun prepareEntree(name: String): Entree? = when (name) {
        "Tossed Salad"   -> Entree.TOSSED_SALAD
        "Salmon on Rice" -> Entree.SALMON_ON_RICE
        else             -> null
    }

    override val specials: List<String>
        get() = TODO("Not yet implemented")

    override fun prepareAppetizer(name: String): Appetizer? { TODO("Not yet implemented") }
    override fun prepareDessert(name: String): Dessert? { TODO("Not yet implemented") }
    override fun receiveCompliment(message: String) { TODO("Not yet implemented") }
}

class Waiter(private val chef: Chef) : KitchenStaff {
    // The waiter can prepare a beverage by himself...
    fun prepareBeverage(name: String): Beverage? = when (name) {
        "Water" -> Beverage.WATER
        "Soda"  -> Beverage.SODA
        else    -> null
    }

    // ... but needs the chef to prepare an entree
    // Manually delegating to the chef for all of these things:
    override val specials: List<String> get() = chef.specials
    override fun prepareEntree(name: String) = chef.prepareEntree(name)
    override fun prepareAppetizer(name: String) = chef.prepareAppetizer(name)
    override fun prepareDessert(name: String) = chef.prepareDessert(name)
    override fun receiveCompliment(message: String) = chef.receiveCompliment(message)

    fun acceptPayment(money: Int) = println("Thank you for paying for your meal")
}

enum class Entree { TOSSED_SALAD, SALMON_ON_RICE }
enum class Beverage { WATER, SODA }

fun main() {
    val waiter = Waiter(Chef())

    val beverage = waiter.prepareBeverage("Soda")
    val entree = waiter.prepareEntree("Salmon on Rice")
}