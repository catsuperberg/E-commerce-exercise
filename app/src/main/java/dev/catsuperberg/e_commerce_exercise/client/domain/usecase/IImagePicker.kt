package dev.catsuperberg.e_commerce_exercise.client.domain.usecase

import android.net.Uri

interface IImagePicker {
    suspend fun pickImage(): Result<Uri>
}
