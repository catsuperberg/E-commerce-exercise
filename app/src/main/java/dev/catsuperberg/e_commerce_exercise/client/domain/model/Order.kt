package dev.catsuperberg.e_commerce_exercise.client.domain.model

import com.google.firebase.Timestamp

data class Order(
    val id: String?,
    val customerName: String,
    val customerPhone: String,
    val customerEmail: String,
    val itemId: String,
    val sum: Float,
    val created: Timestamp?,
    val fulfilled: Boolean,
    val canceled: Boolean
)
