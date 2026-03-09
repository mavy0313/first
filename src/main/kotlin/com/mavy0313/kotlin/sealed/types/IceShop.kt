package com.mavy0313.kotlin.sealed.types

import kotlin.random.Random

enum class Size { CUP, BUCKET, BAG }

//sealed interface Request {
sealed class Request {
//    val id: Int = Random.nextInt()
    val id: Int = Random.nextInt(0, Int.MAX_VALUE)
}

class OrderRequest(val size: Size) : Request()
class RefundRequest(val size: Size, val reason: String) : Request()
class SupportRequest(val text: String) : Request()

object FrontDesk {
    fun receive(request: Request) {
        println("Handling request #${request.id}")
        when (request) {
            is OrderRequest  -> IceCubeFactory.fulfillOrder(request)
            is RefundRequest -> IceCubeFactory.fulfillRefund(request)
            is SupportRequest -> HelpDesk.handle(request)
        }
    }
}

object IceCubeFactory {
    fun fulfillOrder(order: OrderRequest) = println("Fulfilling order #${order.id}")
    fun fulfillRefund(refund: RefundRequest) = println("Fulfilling refund #${refund.id}")
}

object HelpDesk {
    fun handle(request: SupportRequest) = println("Help desk is handling ${request.id}")
}

fun main() {
//    val order = OrderRequest(123, Size.CUP)
//    FrontDesk.receive(order)
//
//    val refund = RefundRequest(456, Size.CUP, "any reason")
//    FrontDesk.receive(refund)
//
//    val request = SupportRequest(789, "I can't open the bag of ice!")
//    FrontDesk.receive(request)
    val order = OrderRequest(Size.CUP)
    FrontDesk.receive(order)

    val refund = RefundRequest(Size.CUP, "any reason")
    FrontDesk.receive(refund)

    val request = SupportRequest("I can't open the bag of ice!")
    FrontDesk.receive(request)
}