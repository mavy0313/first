package com.mavy0313.reactor.eherrera.exercise1

import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

fun main() {
    // Create a simple Publisher
    val publisher = createPublisher();

    // Convert the Publisher to a Mono using fromDirect()
    val mono = Mono.fromDirect(publisher);
//    val mono = Mono.from(publisher);

    // Subscribe to the Mono
    mono.subscribe(
        { value -> println("Received: $value") },
        { error -> println("Error: $error") },
        { println("Completed") }
    )
}

private fun createPublisher(): Publisher<Int> {
    // TODO: Create a Flux publisher that emits two values
    return Flux.just(1, 2)
}
