package com.mavy0313.kotlin.scopes

val pi = 3.14


class Circle(var radius: Double) {
    val diameter = radius * 2

    fun circumference(): Double {
        val result = pi * diameter
        return result
    }
}

fun createCircles(radii: List<Double>): List<Circle> {
    return radii.map { radius ->
        Circle(radius)
    }
}