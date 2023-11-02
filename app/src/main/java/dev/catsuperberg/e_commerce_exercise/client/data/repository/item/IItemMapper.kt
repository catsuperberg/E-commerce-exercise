package dev.catsuperberg.e_commerce_exercise.client.data.repository.item

import com.google.firebase.firestore.QueryDocumentSnapshot
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.model.NewItem

interface IItemMapper {
    fun map(document: QueryDocumentSnapshot): Item?
    fun map(item: Item) : Map<String, Any>
    fun map(item: NewItem) : Map<String, Any>
}
