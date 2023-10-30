package dev.catsuperberg.e_commerce_exercise.client.data.repository.item

import com.google.firebase.firestore.QueryDocumentSnapshot
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import java.util.Random

//TODO throwaway class for until data ingest works
class PlaceholderItemMapper : IItemMapper {
    private val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private val random = Random()

    override fun map(document: QueryDocumentSnapshot): Item? {
        return Item(
            id = document.id,
            name = document.getString(ItemSchema.name) ?: randomString(12),
            description = document.getString(ItemSchema.description),
            price = document.getDouble(ItemSchema.price)?.toFloat() ?: random.nextFloat(),
            available = document.getBoolean(ItemSchema.available) ?: true,
            pathGS = document.getString(ItemSchema.pathGS),
            pathDownload = document.getString(ItemSchema.pathDownload),
        )
    }

    private fun randomString(length: Int): String {
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }
}
