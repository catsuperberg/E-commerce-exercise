package dev.catsuperberg.e_commerce_exercise.client.domain.usecase

import android.net.Uri
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.model.NewItem

interface IItemUpdater {
    suspend fun storeItem(item: NewItem, imageUri: Uri): Result<String>
    suspend fun updateItem(item: Item, imageUri: Uri?): Result<String>
    suspend fun disposeOfItem(id: String): Result<String>
}
