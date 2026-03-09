package com.mavy0313.kotlin.abstract.and.open.classes

//class Car {
//    private var speed = 0.0
//    private fun makeEngineSound() = println("Vrrrrrr...")
//
//    fun accelerate() {
//        speed += 1.0
//        makeEngineSound()
//    }
//}

//abstract class Car(private val acceleration: Double = 1.0) {
//    private var speed = 0.0
////    private fun makeEngineSound() = println("Vrrrrrr...")
////    protected fun makeEngineSound() = println("Vrrrrrr...")
////    protected abstract fun makeEngineSound()
//    protected open fun makeEngineSound() = println("Vrrrrrr...")
//
//    fun accelerate() {
//        speed += acceleration
//        makeEngineSound()
//    }
//}

open class Car(private val acceleration: Double = 1.0) {
    protected var speed = 0.0
        private set
    //    private fun makeEngineSound() = println("Vrrrrrr...")
//    protected fun makeEngineSound() = println("Vrrrrrr...")
//    protected abstract fun makeEngineSound()
    protected open fun makeEngineSound() = println("Vrrrrrr...")

    fun accelerate() {
        speed += acceleration
        makeEngineSound()
    }
}

//class Clunker : Car(0.25)
//class Clunker(acceleration: Double) : Car(acceleration)
class Clunker(acceleration: Double) : Car(acceleration) {
    override fun makeEngineSound() = println("putt-putt-putt")
}

class MuscleCar : Car(5.0) {
    override fun makeEngineSound() = when {
        speed < 10.0 -> println("Vrooooom")
        speed < 20.0 -> println("Vrooooooooom")
        else         -> println("Vrooooooooooooooooooom!")
    }
}

class SimpleCar(acceleration: Double) : Car(acceleration)

fun main() {
//    val myCar = Car()
//    val myCar = Clunker()
    val myCar = Clunker(0.25)
    myCar.accelerate()

    val simpleCar = SimpleCar(1.2)
    simpleCar.accelerate()

    val car = Car()
}