package dev.catsuperberg.e_commerce_exercise.client.domain.service

import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item

interface IItemMessageComposer {
    fun composeShareDetails(item: Item): String
}
