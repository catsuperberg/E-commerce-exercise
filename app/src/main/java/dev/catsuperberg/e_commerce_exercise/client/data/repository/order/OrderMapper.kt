package dev.catsuperberg.e_commerce_exercise.client.data.repository.order

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QueryDocumentSnapshot
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

    override fun map(document: QueryDocumentSnapshot): Order? {
        val customerName = document.getString(OrderSchema.customerName) ?: return null
        val customerPhone = document.getString(OrderSchema.customerPhone) ?: return null
        val customerEmail = document.getString(OrderSchema.customerEmail) ?: return null
        val itemId = document.getString(OrderSchema.itemId) ?: return null
        val sum = document.getDouble(OrderSchema.sum) ?: return null
        val created = document.getTimestamp(OrderSchema.created) ?: return null
        val fulfilled = document.getBoolean(OrderSchema.fulfilled) ?: return null
        val canceled = document.getBoolean(OrderSchema.canceled) ?: return null
        return Order(
            id = document.id,
            customerName = customerName,
            customerPhone = customerPhone,
            customerEmail = customerEmail,
            itemId = itemId,
            sum = sum.toFloat(),
            created = created,
            fulfilled = fulfilled,
            canceled = canceled
        )
    }
}
