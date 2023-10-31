package dev.catsuperberg.e_commerce_exercise.client.domain.usecase

import dev.catsuperberg.e_commerce_exercise.client.data.repository.order.IOrderEndPoint
import dev.catsuperberg.e_commerce_exercise.client.domain.model.CustomerContactDetails
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Order

class OrderRegistration(private val endPoint: IOrderEndPoint) : IOrderRegistration {
    override suspend fun open(item: Item, customer: CustomerContactDetails): Result<String> {
        val order = Order(
            id = null,
            customerName = customer.name,
            customerPhone = customer.phone,
            customerEmail = customer.email,
            itemId = item.id,
            sum = item.price,
            created = null,
            fulfilled = false,
            canceled = false
        )
        return endPoint.create(order)
    }
}
