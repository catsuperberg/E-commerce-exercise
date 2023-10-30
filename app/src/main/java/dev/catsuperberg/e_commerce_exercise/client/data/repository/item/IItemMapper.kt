package dev.catsuperberg.e_commerce_exercise.client.data.repository.item

import com.google.firebase.firestore.QueryDocumentSnapshot
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item

interface IItemMapper {
    fun map(document: QueryDocumentSnapshot): Item?
}
