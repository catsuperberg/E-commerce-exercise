package dev.catsuperberg.e_commerce_exercise.client.domain.usecase

import android.net.Uri
import dev.catsuperberg.e_commerce_exercise.client.data.repository.item.IItemEndPoint
import dev.catsuperberg.e_commerce_exercise.client.data.repository.item.IItemImageEmbedding
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.model.NewItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ItemUpdater(
    private val endPoint: IItemEndPoint,
    private val imageEmbedding: IItemImageEmbedding,
) : IItemUpdater {
    private val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun storeItem(item: NewItem, imageUri: Uri): Result<String> = suspendCoroutine { continuation ->
        scope.launch {
            imageEmbedding.embed(item, imageUri)
                .onSuccess { newItem ->
                    continuation.resume(endPoint.upload(newItem))
                }
                .onFailure { e ->
                    continuation.resume(Result.failure(e))
                }
        }
    }

    override suspend fun updateItem(item: Item, imageUri: Uri?): Result<String> = suspendCoroutine { continuation ->
        imageUri?.let {
                image -> uploadWithNewImage(item, image, continuation)
        } ?: scope.launch { continuation.resume(endPoint.upload(item)) }
    }

    private fun uploadWithNewImage(
        item: Item,
        image: Uri,
        continuation: Continuation<Result<String>>
    ) = scope.launch {
        imageEmbedding.embed(item, image)
            .onSuccess { newItem ->
                continuation.resume(endPoint.upload(newItem))
            }
            .onFailure { e ->
                continuation.resume(Result.failure(e))
            }
    }

    override suspend fun disposeOfItem(id: String): Result<String> = endPoint.delete(id)
}
