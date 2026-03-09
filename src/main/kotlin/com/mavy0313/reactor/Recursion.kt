package com.mavy0313.reactor

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

fun main() {
    recursiveService(1)
        .doOnNext { integer: Int -> println("Final value: $integer") }
        .subscribe()
}

fun incrementIntegerService(current: Int): Mono<Int> {
    println("Inside incrementIntegerService. Current value: $current")
    return Mono.just(current).map { integer1 -> current + 1 }
}

fun recursiveService(number: Int): Flux<Int> {
    return Flux.just(number)
        .flatMap { incrementIntegerService(it) }
        .filter { integer -> integer <= 3 } //Stop condition: Stop after 10 recursions
        .doOnNext { integer -> println("Before call:$integer") }
        .flatMap { integer -> recursiveService(integer).defaultIfEmpty(integer) }
}