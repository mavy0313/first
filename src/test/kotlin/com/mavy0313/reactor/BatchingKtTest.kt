package com.mavy0313.reactor

import com.mavy0313.kotlin.doSomethingUsefulTwo
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.GroupedFlux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*


class BatchingKtTest {
    @Test
    fun grouping() {
        StepVerifier.create<String>(
            Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                .groupBy { i: Int -> if (i % 2 == 0) "even" else "odd" }
                .concatMap { g: GroupedFlux<String, Int> ->
                    g.defaultIfEmpty(-1) //if empty groups, show them
                        .map { obj: Int? -> java.lang.String.valueOf(obj) } //map to string
                        .startWith(g.key())
                } //start with the group's key
        )
            .expectNext("odd", "1", "3", "5", "11", "13")
            .expectNext("even", "2", "4", "6", "12")
            .verifyComplete()
    }

    @Test
    fun window() {
        StepVerifier.create(
            Flux.range(1, 10)
                .window(5, 3) //overlapping windows
                .concatMap { g -> g.defaultIfEmpty(-1) } //show empty windows as -1
        )
            .expectNext(1, 2, 3, 4, 5)
            .expectNext(4, 5, 6, 7, 8)
            .expectNext(7, 8, 9, 10)
            .expectNext(10)
            .verifyComplete()
    }

    @Test
    fun `window while`() {
        StepVerifier.create(
            Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                .windowWhile { i: Int -> i % 2 == 0 }
                .concatMap { g -> g.defaultIfEmpty(-1) }
        )
            .expectNext(-1, -1, -1) //respectively triggered by odd 1 3 5
            .expectNext(2, 4, 6) // triggered by 11
            .expectNext(12) // triggered by 13
            // however, no empty completion window is emitted (would contain extra matching elements)
            .verifyComplete()
    }

    @Test
    fun buffer() {
        StepVerifier.create(
            Flux.range(1, 10)
                .buffer(5, 3) //overlapping buffers
        )
            .expectNext(listOf(1, 2, 3, 4, 5))
            .expectNext(listOf(4, 5, 6, 7, 8))
            .expectNext(listOf(7, 8, 9, 10))
            .expectNext(Collections.singletonList(10))
            .verifyComplete();
    }

    @Test
    fun `buffer while`() {
        StepVerifier.create(
            Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                .bufferWhile { i: Int -> i % 2 == 0 }
        )
            .expectNext(listOf(2, 4, 6)) // triggered by 11
            .expectNext(listOf(12)) // triggered by 13
            .verifyComplete()
    }

    @Test
    fun `flatMap on empty mono completes`() {
        StepVerifier.create(
            Mono.empty<Unit>()
                .flatMap {
                    Mono.just(Unit)
                }
        )
//            .expectNext(Unit) // flatMap is not executed
            .verifyComplete()
    }

    @Test
    fun `then on empty mono`() {
        StepVerifier.create(
            Mono.empty<Unit>()
                .then (
                    Mono.just(Unit)
                )
        )
            .expectNext(Unit)
            .verifyComplete()
    }

    @Test
    fun `switchIfEmpty on empty mono`() {
        StepVerifier.create(
            Mono.empty<Unit>()
                .switchIfEmpty (
                    Mono.just(Unit)
                )
        )
            .expectNext(Unit) // triggered by 13
            .verifyComplete()
    }

    @Test
    fun `doOnNext is skipped on empty mono`() {
        StepVerifier.create(
            Mono.empty<Unit>()
                .doOnNext {
                    Mono.just(Unit)
                }
        )
//            .expectNext(Unit) // triggered by 13
            .verifyComplete()
    }

    @Test
    fun `doOnSuccess is skipped on empty mono`() {
        StepVerifier.create(
            Mono.empty<Unit>()
                .doOnSuccess {
                    Mono.just(Unit)
                }
        )
//            .expectNext(Unit) // triggered by 13
            .verifyComplete()
    }

    @Test
    fun `flatMap executes on mono with emptyList`() {
        StepVerifier.create(
            Mono.just(emptyList<String>())
                .flatMap {
                    Mono.just(Unit)
                }
        )
            .expectNext(Unit)
            .verifyComplete()
    }

    @Test
    fun `then on empty mono2`() {
//        val actions = listOf(1,2)
        val actions = listOf(1)
        StepVerifier.create(
            Mono.just(true)
                .flatMap {
                    actions.map {
                        Mono.just(Unit).doOnNext { println("LOG inside actions.map") }
                            .then(
                                Mono.just(Unit).doOnNext { println("LOG inside then") }
                            )
                    }
                    Mono.just(Unit).doOnNext { println("LOG inside flatMap") }
                }
        )
            .expectNext(Unit)
            .verifyComplete()
    }

//    @Test
//    fun `then on empty mono3`() {
////        val actions = listOf(1,2)
//        val actions = listOf(1)
//        StepVerifier.create(
//            actions.map {
//                Mono.just(Unit).doOnNext { println("LOG inside actions.map") }
//                    .then(
//                        Mono.just(Unit).doOnNext { println("LOG inside then") }
//                    )
//            }
//        )
//            .expectNext(Unit)
//            .verifyComplete()
//    }

    @Test
    fun `exception before then`() {
        val mono1 = Mono.error<Unit> { RuntimeException("mono 1 error") }
        StepVerifier.create(
            mono1
                .doOnError { println("Error before then") } // prints
                .then (
                    Mono.just(Unit)
                        .doOnError { println("Error after then") } // doesn't print
                )
        )
            .expectError()
            .verify()
    }

    @Test
    fun `exception after then`() {
        val mono1 = Mono.empty<Unit>()
        val mono2 = Mono.error<Unit> { RuntimeException("mono 2 error") }
        StepVerifier.create(
            mono1
                .doOnError { println("Error before then") } // doesn't print
                .then(
                    mono2
                        .doOnError { println("Error after then") } // prints
                )
        )
            .expectError()
            .verify()
    }

    @Test
    fun `switchIfEmpty using 2 flatMaps`() {
        val source = Mono.just("Data")
        StepVerifier.create(
            source
                .flatMap { data ->
                    Mono.just("$data flatMap1")
                }
                .flatMap { data ->
                    Mono.just("$data flatMap2")
                }
                .switchIfEmpty(
                    Mono.just("switchIfEmpty")
                )
        )
            .expectNext("Data flatMap1 flatMap2")
            .verifyComplete()
    }

    @Test
    fun `switchIfEmpty using 2 flatMaps first is empty`() {
        val source = Mono.just("Data")
        StepVerifier.create(
            source
                .flatMap { data ->
//                    Mono.just("$data flatMap1")
                    Mono.empty<String>()
                }
                .flatMap { data ->
                    Mono.just("$data flatMap2")
                }
                .switchIfEmpty(
                    Mono.just("switchIfEmpty")
                )
        )
            .expectNext("switchIfEmpty")
            .verifyComplete()
    }

    @Test
    fun `switchIfEmpty using 2 flatMaps second is empty`() {
        val source = Mono.just("Data")
        StepVerifier.create(
            source
                .flatMap { data ->
                    Mono.just("$data flatMap1")
                }
                .flatMap { data ->
                    Mono.empty<String>()
                }
                .switchIfEmpty(
                    Mono.just("switchIfEmpty")
                )
        )
            .expectNext("switchIfEmpty")
            .verifyComplete()
    }
}