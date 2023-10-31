package dev.catsuperberg.e_commerce_exercise.client.data.repository.order

import com.google.firebase.firestore.FieldValue
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Order

class OrderMapper : IOrderMapper {
    override fun map(order: Order): Map<String, Any> = hashMapOf(
            OrderSchema.customerName to order.customerName,
            OrderSchema.customerPhone to order.customerPhone,
            OrderSchema.customerEmail to order.customerEmail,
            OrderSchema.itemId to order.itemId,
            OrderSchema.sum to order.sum,
            OrderSchema.created to (order.created ?: FieldValue.serverTimestamp()),
            OrderSchema.fulfilled to order.fulfilled,
            OrderSchema.canceled to order.canceled,
        )
}
