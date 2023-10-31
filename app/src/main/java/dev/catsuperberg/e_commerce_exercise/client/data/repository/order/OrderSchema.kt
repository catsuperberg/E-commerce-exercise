package dev.catsuperberg.e_commerce_exercise.client.data.repository.order

object OrderSchema {
    val collectionName = "orders"

    val customerName = "customer_name"
    val customerPhone = "customer_phone"
    val customerEmail = "customer_email"
    val itemId = "item_id"
    val sum = "sum"
    val created = "created"
    val fulfilled = "fulfilled"
    val canceled = "canceled"
}
