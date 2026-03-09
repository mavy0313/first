package com.mavy0313.kotlin.generics.covariance.and.contravariance

open class Animal

open class Dog : Animal()

open class Cat : Animal()

class Schnauzer : Dog()

interface Gizmo {
    fun match(subject: Cat): Dog
}

interface SubGizmo : Gizmo {
    override fun match(subject: Cat): Schnauzer
}

class SubGizmoImpl : SubGizmo {
    override fun match(subject: Cat): Schnauzer {
        return Schnauzer()
    }
}

fun main() {
    val subGizmo = createSubGizmo()
    val superGizmo = subGizmo as Gizmo

    val dog = superGizmo.match(Cat())     // Inferred type is Dog
    val schnauzer = subGizmo.match(Cat()) // Inferred type is Schnauzer
}

fun createSubGizmo(): SubGizmoImpl {
    return SubGizmoImpl()
}
