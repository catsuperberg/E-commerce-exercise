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
    val pathGs: String?,
    val pathDownload: String?,
) : Parcelable

data class NewItem(
    val name: String,
    val description: String,
    val price: Float,
    val available: Boolean,
    val pathGs: String? = null,
    val pathDownload: String? = null,
) {
    companion object {
        fun fromFull(item: Item) = NewItem(
            item.name,
            item.description ?: "",
            item.price,
            item.available,
            item.pathGs,
            item.pathDownload
        )
    }
}
