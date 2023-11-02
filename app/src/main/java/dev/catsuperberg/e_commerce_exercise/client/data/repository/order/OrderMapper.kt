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
        if (!document.isOrder())
            return null
        return Order(
            id = document.id,
            customerName = document.getString(OrderSchema.customerName)!!,
            customerPhone = document.getString(OrderSchema.customerPhone)!!,
            customerEmail = document.getString(OrderSchema.customerEmail)!!,
            itemId = document.getString(OrderSchema.itemId)!!,
            sum = document.getDouble(OrderSchema.sum)!!.toFloat(),
            created = document.getTimestamp(OrderSchema.created)!!,
            fulfilled = document.getBoolean(OrderSchema.fulfilled)!!,
            canceled = document.getBoolean(OrderSchema.canceled)!!
        )
    }

    private fun QueryDocumentSnapshot.isOrder(): Boolean = getString(OrderSchema.customerName) != null &&
            getString(OrderSchema.customerPhone) != null &&
            getString(OrderSchema.customerEmail) != null &&
            getString(OrderSchema.itemId) != null &&
            getDouble(OrderSchema.sum) != null &&
            getTimestamp(OrderSchema.created) != null &&
            getBoolean(OrderSchema.fulfilled) != null &&
            getBoolean(OrderSchema.canceled) != null
}
