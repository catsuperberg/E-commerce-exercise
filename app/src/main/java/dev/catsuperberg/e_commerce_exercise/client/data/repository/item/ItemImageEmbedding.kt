package dev.catsuperberg.e_commerce_exercise.client.data.repository.item

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.model.NewItem
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ItemImageEmbedding : IItemImageEmbedding {
    private val storage = Firebase.storage
    private val pathStart = "gs://${storage.reference.bucket}/"

    override suspend fun embed(item: Item, imageUri: Uri): Result<Item> = suspendCoroutine { continuation ->
        val (fileRef, uploadTask) = getPrerequisites(imageUri)

        uploadTask.addOnCompleteListener { _ ->
            fileRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                val newItem = item.copy(
                    pathGs = "$pathStart${fileRef.path}",
                    pathDownload = downloadUrl.toString()
                )
                continuation.resume(Result.success(newItem))
            }
        }.addOnFailureListener { e ->
            continuation.resume(Result.failure(e))
        }
    }

    override suspend fun embed(item: NewItem, imageUri: Uri): Result<NewItem> = suspendCoroutine { continuation ->
        val (fileRef, uploadTask) = getPrerequisites(imageUri)

        uploadTask.addOnCompleteListener { _ ->
            fileRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                val newItem = item.copy(
                    pathGs = "$pathStart${fileRef.path}",
                    pathDownload = downloadUrl.toString()
                )
                continuation.resume(Result.success(newItem))
            }
        }.addOnFailureListener { e ->
            continuation.resume(Result.failure(e))
        }
    }

    private fun getPrerequisites(imageUri: Uri): Pair<StorageReference, UploadTask> {
        val fileName = UUID.randomUUID().toString()
        val fileRef = storage.reference.child("${ItemSchema.imageGsPath}/$fileName.png")
        val uploadTask = fileRef.putFile(imageUri)
        return Pair(fileRef, uploadTask)
    }
}
