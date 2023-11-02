package dev.catsuperberg.e_commerce_exercise.client.data.repository.order

import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Order
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class OrderEndPoint(private val mapper: IOrderMapper) : IOrderEndPoint {
    private val db = Firebase.firestore

    override suspend fun create(order: Order): Result<String> {
        val mappedData = mapper.map(order)
        return suspendCoroutine { continuation ->
            db.collection(OrderSchema.collectionName)
                .add(mappedData)
                .addOnSuccessListener { documentReference ->
                    continuation.resume(Result.success(documentReference.toString()))
                }
                .addOnFailureListener { e ->
                    continuation.resume(Result.failure(e))
                }
        }
    }

    override fun fulfill(id: String) {
        val data = hashMapOf(OrderSchema.fulfilled to true)
        mergeValues(id, data)
    }

    override fun cancel(id: String) {
        val data = hashMapOf(OrderSchema.canceled to true)
        mergeValues(id, data)
    }

    private fun mergeValues(id: String, data: HashMap<String, Boolean>) {
        db.collection(OrderSchema.collectionName).document(id)
            .set(data, SetOptions.merge())
    }
}
