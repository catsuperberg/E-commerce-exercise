package dev.catsuperberg.e_commerce_exercise.client.domain.usecase

import dev.catsuperberg.e_commerce_exercise.client.data.repository.order.IOrderEndPoint

class OrderUpdater(private val endPoint: IOrderEndPoint) : IOrderUpdater {
    override fun fulfill(id: String) {
        endPoint.fulfill(id)
    }

    override fun cancel(id: String) {
        endPoint.cancel(id)
    }
}
