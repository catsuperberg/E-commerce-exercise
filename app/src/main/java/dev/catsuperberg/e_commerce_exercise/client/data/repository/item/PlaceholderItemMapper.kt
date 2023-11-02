package dev.catsuperberg.e_commerce_exercise.client.data.repository.item

import com.google.firebase.firestore.QueryDocumentSnapshot
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.model.NewItem
import java.util.Random

//TODO throwaway class for until data ingest works
class PlaceholderItemMapper : IItemMapper {
    private val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private val random = Random()

    override fun map(document: QueryDocumentSnapshot): Item {
        return Item(
            id = document.id,
            name = document.getString(ItemSchema.name) ?: randomString(12),
            description = document.getString(ItemSchema.description),
            price = document.getDouble(ItemSchema.price)?.toFloat() ?: random.nextFloat(),
            available = document.getBoolean(ItemSchema.available) ?: false,
            pathGs = document.getString(ItemSchema.pathGs),
            pathDownload = document.getString(ItemSchema.pathDownload),
        )
    }

    override fun map(item: Item): Map<String, Any> = map(NewItem.fromFull(item))

    override fun map(item: NewItem): Map<String, Any> = hashMapOf(
        ItemSchema.name to item.name,
        ItemSchema.description to item.description,
        ItemSchema.price to item.price,
        ItemSchema.available to item.available,
        ItemSchema.pathGs to (item.pathGs ?: ""),
        ItemSchema.pathDownload to (item.pathDownload ?: "")
    )

    private fun randomString(length: Int): String {
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }
}
