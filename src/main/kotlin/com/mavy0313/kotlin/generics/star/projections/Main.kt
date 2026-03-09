package com.mavy0313.kotlin.generics.star.projections

//interface Group<T> {
//    fun insert(item: T): Unit
//    fun fetch(): T
//}

interface SuperGroup {
    fun insert(item: Nothing): Unit
    fun fetch(): Any?
}

//interface Group<T : Animal> {
//    fun insert(member: T): Unit
//    fun fetch(): T
//}

interface Group<T : Dog> {
    fun insert(member: T): Unit
    fun fetch(): T
}

interface Animal

interface Dog : Animal

fun readIn(group: Group<in Nothing>) {
    // Inferred type of `item` is `Any?`
    val item = group.fetch()
}

//fun readOut(group: Group<out Animal>) {
fun readOut(group: Group<out Dog>) {
    // Inferred type of `item` is `Animal`
    val item = group.fetch()
}

fun readStar(group: Group<*>) {
    // Inferred type of `item` is `Animal`
    val item = group.fetch()
}

fun acceptAnyList(list: List<Any?>) {}
fun acceptStarList(list: List<*>) {}

fun acceptAnyArray(array: Array<Any?>) {}
fun acceptStarArray(array: Array<*>) {}

fun main() {
//    val group = Group
//    readIn()
    val listOfStrings: List<String> = listOf("Hello", "Kotlin", "World")

    acceptAnyList(listOfStrings)
    acceptStarList(listOfStrings)

    val arrayOfStrings = arrayOf("Hello", "Kotlin", "World")
//    acceptAnyArray(arrayOfStrings)  // Compiler error here
    acceptStarArray(arrayOfStrings)
}