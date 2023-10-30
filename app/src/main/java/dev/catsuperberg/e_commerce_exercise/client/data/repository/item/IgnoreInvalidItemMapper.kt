package dev.catsuperberg.e_commerce_exercise.client.data.repository.item

import com.google.firebase.firestore.QueryDocumentSnapshot
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item

class IgnoreInvalidItemMapper : IItemMapper {
    override fun map(document: QueryDocumentSnapshot): Item? {
        if (!document.isItem())
            return null
        return Item(
            id = document.id,
            name = document.getString(ItemSchema.name)!!,
            description = document.getString(ItemSchema.description),
            price = document.getDouble(ItemSchema.price)?.toFloat()!!,
            available = document.getBoolean(ItemSchema.available)!!,
            pathGS = document.getString(ItemSchema.pathGS),
            pathDownload = document.getString(ItemSchema.pathDownload),
        )
    }

    private fun QueryDocumentSnapshot.isItem(): Boolean = getString(ItemSchema.name) != null &&
            getDouble(ItemSchema.price) != null &&
            getBoolean(ItemSchema.available) != null
}
