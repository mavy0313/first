package com.mavy0313.reactor.eherrera.mapflatmap

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


fun main() {
    Mono.just(1)
        .map { it * 2 }
        .subscribe { println(it) }

    Flux.just(1)
        .map { it * 2 }
        .subscribe { println(it) }

    val monoString = Mono.just("2022-01-01")
    val monoDate: Mono<LocalDate> = monoString.map(stringToDateFunction)

    monoDate.subscribe { d: LocalDate ->
        println(
            d.format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
            )
        )
    }

    val fluxString = Flux.just<String?>(
        "2022-01-02", "2022-01-03", "2022-01-04"
    )
    val fluxDate: Flux<LocalDate> = fluxString.map(stringToDateFunction)

    fluxDate.subscribe{ d: LocalDate ->
        println(
            d.format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
            )
        )
    }

    val monoInt = Mono.just(1)
    val monoFlat = monoInt
        .flatMap { i: Int -> Mono.just("i: $i") }

// Remember, nothing happens until you subscribe
    monoFlat.subscribe { x: String -> println(x) }
}

var stringToDateFunction: (String) -> LocalDate = { s ->
    LocalDate.parse(
        s,
        DateTimeFormatter.ofPattern(
            "yyyy-MM-dd",
            Locale.ENGLISH
        )
    )
}
