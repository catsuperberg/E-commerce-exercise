package dev.catsuperberg.e_commerce_exercise.client.domain.usecase

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.service.IItemMessageComposer
import dev.catsuperberg.e_commerce_exercise.client.domain.service.IUriImageAccess

class ItemDetailsSender(
    private val context: Context,
    private val imageAccess: IUriImageAccess,
    private val messageComposer: IItemMessageComposer
) : IItemDetailsSender {
    private val firebaseStorage = FirebaseStorage.getInstance()
    private val pathStart = "gs://${firebaseStorage.reference.bucket}/"

    override fun sendToMessenger(item: Item) {
        item.pathGS?.also { gsPath -> sendWithPicture(gsPath, item) } ?: sendIntent(listOf { it.applyText(item) } )
    }

    private fun sendWithPicture(gsPath: String, item: Item) {
        val storageReference = firebaseStorage.getReference(getRelativePath(gsPath))
        storageReference.getBytes(1024 * 1024)
            .addOnSuccessListener { bytes ->
                val uri = imageAccess.cacheImageAndGetUri(getFileName(gsPath), bytes)
                sendIntent(listOf({ it.applyText(item) }, { it.applyImage(uri) }))
            }
            .addOnFailureListener { e ->
                Log.d("E", "Couldn't load image: ${e.message}")
            }
    }

    private fun sendIntent(intentEnhancements: List<(intent: Intent) -> Unit>) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            intentEnhancements.forEach { it.invoke(this) }
        }
        context.startActivity(intent)
    }

    private fun Intent.applyImage(imageUri: Uri) {
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        setDataAndType(imageUri, context.contentResolver.getType(imageUri));
        putExtra(Intent.EXTRA_STREAM, imageUri)
    }

    private fun Intent.applyText(item: Item) {
        type = "text/plain"
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        putExtra(Intent.EXTRA_SUBJECT, item.name)
        putExtra(Intent.EXTRA_TEXT,messageComposer.composeShareDetails(item))
    }

    private fun getFileName(gsPath: String) = gsPath.substring(gsPath.lastIndexOf("/") + 1)
    private fun getRelativePath(gsPath: String) = gsPath.replace(pathStart, "")
}
