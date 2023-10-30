package dev.catsuperberg.e_commerce_exercise.client.domain.usecase

import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item

interface IItemDetailsSender {
    fun sendToMessenger(item: Item)
}
