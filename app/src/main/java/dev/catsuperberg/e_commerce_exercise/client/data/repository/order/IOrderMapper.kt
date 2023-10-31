package dev.catsuperberg.e_commerce_exercise.client.data.repository.order

import dev.catsuperberg.e_commerce_exercise.client.domain.model.Order

interface IOrderMapper {
    fun map(order: Order) : Map<String, Any>
}
