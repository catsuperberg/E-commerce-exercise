package dev.catsuperberg.e_commerce_exercise.client.data.repository.item

import com.google.firebase.firestore.QueryDocumentSnapshot
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.model.NewItem

class IgnoreInvalidItemMapper : IItemMapper {
    override fun map(document: QueryDocumentSnapshot): Item? {
        if (!document.isItem())
            return null
        return Item(
            id = document.id,
            name = document.getString(ItemSchema.name)!!,
            description = document.getString(ItemSchema.description),
            price = document.getDouble(ItemSchema.price)!!.toFloat(),
            available = document.getBoolean(ItemSchema.available)!!,
            pathGs = document.getString(ItemSchema.pathGs),
            pathDownload = document.getString(ItemSchema.pathDownload),
        )
    }

    override fun map(item: Item): Map<String, Any> = map(NewItem.fromFull(item))

    override fun map(item: NewItem): Map<String, Any> = hashMapOf(
        ItemSchema.name to item.name,
        ItemSchema.description to (item.description ?: ""),
        ItemSchema.price to item.price,
        ItemSchema.available to item.available,
        ItemSchema.pathGs to (item.pathGs ?: ""),
        ItemSchema.pathDownload to (item.pathDownload ?: "")
    )

    private fun QueryDocumentSnapshot.isItem(): Boolean = getString(ItemSchema.name) != null &&
            getDouble(ItemSchema.price) != null &&
            getBoolean(ItemSchema.available) != null
}
