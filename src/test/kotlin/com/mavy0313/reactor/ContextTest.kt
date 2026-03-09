package com.mavy0313.reactor

import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import reactor.util.context.Context
import reactor.util.context.ContextView

class ContextTest {

    @Test
    fun `context`() {
        val key = "message"
        val r = Mono.just("Hello")
            .flatMap { s ->
                Mono.deferContextual { ctx: ContextView ->
                    Mono.just(s + " " + ctx.get(key))
                }
            }
            .contextWrite { ctx -> ctx.put(key, "World") }

        StepVerifier.create(r)
            .expectNext("Hello World")
            .verifyComplete()
    }

    @Test
    fun `contextWrite above reading context`() {
        val key = "message"
        val r = Mono.just("Hello")
            .contextWrite { ctx: Context ->
                ctx.put(key, "World")
            }
            .flatMap { s: String ->
                Mono.deferContextual { ctx: ContextView ->
                    Mono.just(
                        s + " " + ctx.getOrDefault(key, "Stranger")
                    )
                }
            }

        StepVerifier.create(r)
            .expectNext("Hello Stranger")
            .verifyComplete()
    }

    @Test
    fun `2 contextWrites closest is used (below)`() {
        val key = "message"
        val r = Mono
            .deferContextual { ctx: ContextView ->
                Mono.just(
                    "Hello " + ctx.get(key)
                )
            }
            .contextWrite { ctx: Context ->
                ctx.put(key, "Reactor")
            }
            .contextWrite { ctx: Context ->
                ctx.put(key, "World")
            }

        StepVerifier.create(r)
            .expectNext("Hello Reactor")
            .verifyComplete()
    }

    @Test
    fun `2 contextWrites operator uses context below`() {
        val key = "message"
        val r = Mono
            .deferContextual { ctx: ContextView ->
                Mono.just(
                    "Hello " + ctx.get(key)
                )
            }
            .contextWrite { ctx: Context ->
                ctx.put(key, "Reactor")
            }
            .flatMap { s: String ->
                Mono.deferContextual { ctx: ContextView ->
                    Mono.just(
                        s + " " + ctx.get(key)
                    )
                }
            }
            .contextWrite { ctx: Context ->
                ctx.put(key, "World")
            }

        StepVerifier.create(r)
            .expectNext("Hello Reactor World")
            .verifyComplete()
    }

    @Test
    fun `2 contextWrites one inside flatMap`() {
        val key = "message"
        val r = Mono.just("Hello")
            .flatMap { s: String ->
                Mono
                    .deferContextual { ctxView: ContextView ->
                        Mono.just(
                            s + " " + ctxView.get(key)
                        )
                    }
            }
            .flatMap { s: String ->
                Mono
                    .deferContextual { ctxView: ContextView ->
                        Mono.just(
                            s + " " + ctxView.get(key)
                        )
                    }
                    .contextWrite { ctx: Context ->
                        ctx.put(key, "Reactor")
                    }
            }
            .contextWrite { ctx: Context ->
                ctx.put(key, "World")
            }

        StepVerifier.create(r)
            .expectNext("Hello World Reactor")
            .verifyComplete()
    }
}