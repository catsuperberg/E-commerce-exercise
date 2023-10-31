package dev.catsuperberg.e_commerce_exercise.client.data.repository.order

import dev.catsuperberg.e_commerce_exercise.client.domain.model.Order

interface IOrderEndPoint {
    suspend fun create(order: Order) : Result<String>
}
