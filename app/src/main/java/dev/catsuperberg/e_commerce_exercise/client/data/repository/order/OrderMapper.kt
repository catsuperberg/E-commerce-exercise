package dev.catsuperberg.e_commerce_exercise.client.data.repository.order

import dev.catsuperberg.e_commerce_exercise.client.domain.model.Order

class OrderMapper : IOrderMapper {
    override fun map(order: Order): Map<String, Any> = hashMapOf<String, Any>(
            OrderSchema.customerName to order.customerName,
            OrderSchema.customerPhone to order.customerPhone,
            OrderSchema.customerEmail to order.customerEmail,
            OrderSchema.itemId to order.itemId,
            OrderSchema.sum to order.sum,
            OrderSchema.fulfilled to order.fulfilled,
            OrderSchema.canceled to order.canceled,
        )
}
