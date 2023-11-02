package dev.catsuperberg.e_commerce_exercise.client.data.repository.item

import com.google.firebase.firestore.QueryDocumentSnapshot
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.model.NewItem

class IgnoreInvalidItemMapper : IItemMapper {
    override fun map(document: QueryDocumentSnapshot): Item? {
        val name = document.getString(ItemSchema.name) ?: return null
        val price = document.getDouble(ItemSchema.price) ?: return null
        val available = document.getBoolean(ItemSchema.available) ?: return null
        return Item(
            id = document.id,
            name = name,
            description = document.getString(ItemSchema.description),
            price = price.toFloat(),
            available = available,
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
}
