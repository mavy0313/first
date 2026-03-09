package com.mavy0313.kotlin.recievers

class Dog {
    fun speak() {
        println("BARK!")
    }
    fun play() {
        this.speak()
    }
}

//fun singleQuoted(original: String) = "'$original'"

fun String.singleQuoted() = "'$this'"

fun main() {
    val fido = Dog()
    fido.speak()

    println("Hi".singleQuoted())

    val title = "The Robots from Planet X3"
    val newTitle = title
        .removePrefix("The ")
        .singleQuoted()
        .uppercase()

    println(newTitle)

    val title2: String? = null
    val newTitle2 = title2?.singleQuoted()
}