package dev.catsuperberg.e_commerce_exercise.client.data.repository.order

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Order
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class OrderEndPoint(private val mapper: IOrderMapper) : IOrderEndPoint {
    val db = Firebase.firestore

//    override fun create(order: Order) {
//        val mappedData = mapper.map(order)
//        db.collection(OrderSchema.collectionName)
//            .add(mappedData)
//            .addOnSuccessListener { documentReference ->
//                Log.d("D", "DocumentSnapshot written with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.d("E", "Error adding document", e)
//            }
//    }

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
}
