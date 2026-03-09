package com.mavy0313.kotlininaction.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds


private var zeroTime = System.currentTimeMillis()

fun log(message: Any?) =
    println("${System.currentTimeMillis() - zeroTime} " +
            "[${Thread.currentThread().name}] $message")

suspend fun doSomethingSlowly() {
    delay(500.milliseconds) //Остановка выполнения функции на 500 миллисекунд
    println("I'm done")
}
//fun main() = runBlocking {
//    doSomethingSlowly()
//}

//}

//fun main() = runBlocking {
//    log("The first, parent, coroutine starts")
//    launch {
//        log("The second coroutine starts and is ready to be suspended")
//        delay(100.milliseconds)
//        log("The second coroutine is resumed")
//    }
//    launch {
//        log("The third coroutine can run in the meantime")
//    }
//    log("The first coroutine has launched two more coroutines")

suspend fun slowlyAddNumbers(a: Int, b: Int): Int {
    log("Waiting a bit before calculating $a + $b")
    delay(100.milliseconds * a)
    return a + b
}

fun main() = runBlocking {
    log("Starting the async computation")
    val myFirstDeferred = async { slowlyAddNumbers(2, 2) } //Запуск новой корутины для каждого вызова async
    val mySecondDeferred = async { slowlyAddNumbers(4, 4) }
    log("Waiting for the deferred value to be available")
    log("The first result: ${myFirstDeferred.await()}") //Ожидание доступности результатов
    log("The second result: ${mySecondDeferred.await()}")
}