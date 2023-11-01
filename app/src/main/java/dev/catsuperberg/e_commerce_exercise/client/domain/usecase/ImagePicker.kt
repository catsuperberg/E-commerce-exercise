package dev.catsuperberg.e_commerce_exercise.client.domain.usecase

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import dev.catsuperberg.e_commerce_exercise.client.domain.exception.AppException.UninitializedException
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface IMediaRequestHoisting {
    fun selectedImage(result: Result<Uri>)
    fun registerRequester(requester: ActivityResultLauncher<PickVisualMediaRequest>)
}

class ImagePicker : IImagePicker, IMediaRequestHoisting {
    private var pickRequester: ActivityResultLauncher<PickVisualMediaRequest>? = null
    private var continuation: Continuation<Result<Uri>>? = null

    override suspend fun pickImage(): Result<Uri> = suspendCoroutine {
        continuation = it
        pickRequester?.apply {
            launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        } ?: returnResult(
            Result.failure(
                UninitializedException("${::pickRequester.name} in ${::ImagePicker.name} haven't been initiated")
            )
        )
    }

    private fun returnResult(result: Result<Uri>) {
        continuation?.resume(result)
        continuation = null
    }

    override fun selectedImage(result: Result<Uri>) {
        returnResult(result)
    }

    override fun registerRequester(requester: ActivityResultLauncher<PickVisualMediaRequest>) {
        pickRequester = requester
    }
}
