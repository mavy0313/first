package com.mavy0313.kotlin.delegation.multiple

interface BarStaff {
    fun prepareBeverage(name: String): Beverage?
    fun receiveCompliment(message: String)
}

class Bartender: BarStaff {
    override fun prepareBeverage(name: String): Beverage? = when (name) {
        "Water"        -> Beverage.WATER
        "Soda"         -> Beverage.SODA
        "Peach Tea"    -> Beverage.PEACH_ICED_TEA
        "Tea-Lemonade" -> Beverage.TEA_LEMONADE
        else           -> null
    }

    override fun receiveCompliment(message: String) =
        println("Bartender received a compliment: $message")
}

interface KitchenStaff {
    val specials: List<String>
    fun prepareEntree(name: String): Entree?
    fun prepareAppetizer(name: String): Appetizer?
    fun prepareDessert(name: String): Dessert?
    fun receiveCompliment(message: String)
}

class Dessert

class Appetizer

class Chef : KitchenStaff {
    override fun prepareEntree(name: String): Entree? = when (name) {
        "Tossed Salad"   -> Entree.TOSSED_SALAD
        "Salmon on Rice" -> Entree.SALMON_ON_RICE
        else             -> null
    }

    override val specials: List<String>
        get() = listOf("special meal1", "special meal2")

    override fun prepareAppetizer(name: String): Appetizer? {
        println("prepareAppetizer")
        return Appetizer()
    }
    override fun prepareDessert(name: String): Dessert? {
        println("prepareDessert")
        return Dessert()
    }
    override fun receiveCompliment(message: String) {
        println("Chef received a compliment: $message")
    }
}

class Waiter(
    private val chef: Chef,
    private val bartender: Bartender
//) : KitchenStaff by chef, BarStaff { manual delegation
) : KitchenStaff by chef, BarStaff by bartender {

    //manual delegation
//    override fun prepareBeverage(name: String): Beverage? = bartender.prepareBeverage(name)

//    override fun prepareBeverage(name: String): Beverage? = bartender.prepareBeverage(name) 'by' delegation

    // ... but needs the chef to prepare an entree
    // Manually delegating to the chef for all of these things:
//    override val specials: List<String> get() = chef.specials

    //partial manual delegation delegation
    override fun prepareEntree(name: String) =
        if (name == "Tossed Salad") Entree.TOSSED_SALAD else chef.prepareEntree(name)
//    override fun prepareAppetizer(name: String) = chef.prepareAppetizer(name)
//    override fun prepareDessert(name: String) = chef.prepareDessert(name)
//    override fun receiveCompliment(message: String) = chef.receiveCompliment(message)

    override fun receiveCompliment(message: String) = when {
        message.contains("entree")   -> chef.receiveCompliment(message)
        message.contains("beverage") -> bartender.receiveCompliment(message)
        else                         -> println("Waiter received compliment: $message")
    }

    fun acceptPayment(money: Int) = println("Thank you for paying for your meal")
}

enum class Entree { TOSSED_SALAD, SALMON_ON_RICE }
enum class Beverage { WATER, SODA, PEACH_ICED_TEA, TEA_LEMONADE }

fun main() {
    val waiter = Waiter(Chef(), Bartender())

    val beverage = waiter.prepareBeverage("Soda")
    println("beverage:$beverage")
    val entree = waiter.prepareEntree("Salmon on Rice")
    println("entree:$entree")
    println("specials:${waiter.specials}")

    val salad = waiter.prepareEntree("Tossed Salad")
    println("salad:$salad")
    waiter.receiveCompliment("The salmon entree was fantastic!")
    waiter.receiveCompliment("The peach tea beverage was fantastic!")
    waiter.receiveCompliment("The service was fantastic!")
}