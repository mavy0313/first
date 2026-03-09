package com.mavy0313.kotlin.generics.`in`.and.outs.of.generic.variance


//two rules for generics
//a subtype parameters must accept at least as supertype (expanding - ok, narrowing - nok)
//a subtype return result must return at most as supertype (narrowing - ok, expanding - nok)

interface Group<T> {
    fun insert(item: T): Unit
    fun fetch(): T
}

fun main() {

}