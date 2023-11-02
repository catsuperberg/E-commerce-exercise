package dev.catsuperberg.e_commerce_exercise.client.data.repository.item

import android.net.Uri
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.model.NewItem

interface IItemImageEmbedding {
    suspend fun embed(item: Item, imageUri: Uri): Result<Item>
    suspend fun embed(item: NewItem, imageUri: Uri): Result<NewItem>
}
