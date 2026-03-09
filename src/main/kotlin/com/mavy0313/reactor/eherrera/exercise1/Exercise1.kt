package com.mavy0313.reactor.eherrera.exercise1

import reactor.core.publisher.Mono
import java.util.Optional

fun main() {
    val optional = Optional.of("value")
    val emptyOptional = Optional.empty<String>()

    val monoWithValue = Mono.justOrEmpty(optional)
    monoWithValue.subscribe(
        { element -> println("Mono with value - Value: $element") },
        { error -> println("Mono with value - Error: ${error.message}") },
        { println("Mono with value complete") }
    )
    monoWithValue.subscribe(
        { element -> println("Mono with value - Value: $element") },
        { error -> println("Mono with value - Error: ${error.message}") },
        { println("Mono with value complete") }
    )

    val monoEmpty = Mono.justOrEmpty(emptyOptional)
    monoEmpty.subscribe(
        { element -> println("Mono empty - Value: $element") },
        { error -> println("Mono empty - Error: ${error.message}") },
        { println("Mono empty complete") }
    )

//    optional.toMono()
    val monoWithValueCold = Mono.defer { monoWithValue }
    monoWithValueCold.subscribe(
        { element -> println("Mono with value cold - Value: $element") },
        { error -> println("Mono with value cold - Error: ${error.message}") },
        { println("Mono with value cold complete") }
    )

    monoWithValueCold.subscribe(
        { element -> println("Mono with value cold - Value: $element") },
        { error -> println("Mono with value cold - Error: ${error.message}") },
        { println("Mono with value cold complete") }
    )
}