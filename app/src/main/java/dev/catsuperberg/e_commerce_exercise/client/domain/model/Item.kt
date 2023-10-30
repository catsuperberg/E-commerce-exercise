package dev.catsuperberg.e_commerce_exercise.client.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val id: String,
    val name: String,
    val description: String?,
    val price: Float,
    val available: Boolean,
    val pathGS: String?,
    val pathDownload: String?,
) : Parcelable
