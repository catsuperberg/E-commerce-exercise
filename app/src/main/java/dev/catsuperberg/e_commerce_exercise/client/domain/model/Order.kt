package dev.catsuperberg.e_commerce_exercise.client.domain.model

data class Order(
    val id: String?,
    val customerName: String,
    val customerPhone: String,
    val customerEmail: String,
    val itemId: String,
    val sum: Float,
    val fulfilled: Boolean,
    val canceled: Boolean
)
