package com.mavy0313.reactor

import reactor.core.publisher.Flux
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers


//inline fun <reified T> TreeNode.findParentOfType(): T? {
//    var p = parent
//    while (p != null && p !is T) {
//        p = p.parent
//    }
//    return p as T?
//}

//inline fun <reified T> membersOf() = T::class.members

//fun main(s: Array<String>) {
//    println(membersOf<StringBuilder>().joinToString("\n"))
//}

fun main(args: Array<String>) {
//    println(membersOf<StringBuilder>().joinToString("\n"))
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")


    Flux.just(10)
        .map(::doSomethingDangerous)
        .onErrorReturn("RECOVERED")
        .doFinally { signalType -> println("doFinally signalType:$signalType") }
        .subscribe()

//    publishOn()
    subscribeOn()
}

fun doSomethingDangerous(number: Int?): Any {
    println("Inside doSomethingDangerous")
    throw RuntimeException("my runtime exception")
}

fun publishOn() {
    println("publishOn")
    val s: Scheduler = Schedulers.newParallel("parallel-scheduler", 4)

    val flux = Flux
        .range(1, 2)
        .map { i: Int ->
            println(Thread.currentThread().name)
            10 + i }
        .publishOn(s)
        .map { i: Int ->
            println(Thread.currentThread().name)
            "value $i" }

    Thread {
        flux.subscribe { x: String? ->
            println(
                x
            )
        }
    }.start()
}

fun subscribeOn() {
    println("subscribeOn")
    val s = Schedulers.newParallel("parallel-scheduler", 4)

    val flux = Flux
        .range(1, 2)
        .map { i: Int ->
            println(Thread.currentThread().name)
            10 + i }
        .subscribeOn(s)
        .map { i: Int ->
            println(Thread.currentThread().name)
            "value $i" }

    Thread {
        flux.subscribe { x: String? ->
            println(
                x
            )
        }
    }.start()
}
