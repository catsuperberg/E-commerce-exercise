package dev.catsuperberg.e_commerce_exercise.client.data.repository.item

import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.model.NewItem

interface IItemEndPoint {
    suspend fun upload(item: Item): Result<String>
    suspend fun upload(item: NewItem): Result<String>
}
