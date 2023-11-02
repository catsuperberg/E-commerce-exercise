package dev.catsuperberg.e_commerce_exercise.client.data.repository.item

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.model.NewItem
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ItemEndPoint(private val mapper: IItemMapper) : IItemEndPoint {
    private val db = Firebase.firestore

    override suspend fun upload(item: Item): Result<String> {
        val mappedData = mapper.map(item)
        return suspendCoroutine { continuation ->
            db.collection(ItemSchema.collectionName).document(item.id)
                .set(mappedData)
                .addOnSuccessListener { _ ->
                    continuation.resume(Result.success(item.id))
                }
                .addOnFailureListener { e ->
                    continuation.resume(Result.failure(e))
                }
        }
    }

    override suspend fun upload(item: NewItem): Result<String> {
        val mappedData = mapper.map(item)
        return suspendCoroutine { continuation ->
            db.collection(ItemSchema.collectionName)
                .add(mappedData)
                .addOnSuccessListener { documentReference ->
                    continuation.resume(Result.success(documentReference.toString()))
                }
                .addOnFailureListener { e ->
                    continuation.resume(Result.failure(e))
                }
        }
    }

    override suspend fun delete(id: String): Result<String> = suspendCoroutine { continuation ->
        db.collection(ItemSchema.collectionName).document(id)
            .delete()
            .addOnSuccessListener { _ ->
                continuation.resume(Result.success(id))
            }
            .addOnFailureListener { e ->
                continuation.resume(Result.failure(e))
            }
    }
}
