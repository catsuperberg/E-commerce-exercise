package dev.catsuperberg.e_commerce_exercise.client.domain.usecase

import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item

interface IItemUpdater {
    fun storeItem(item: Item)
    fun disposeOfItem(id: String)
}
