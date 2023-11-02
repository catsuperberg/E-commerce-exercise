package dev.catsuperberg.e_commerce_exercise.client.domain.service

import android.content.Context
import dev.catsuperberg.e_commerce_exercise.client.R
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item

class ItemMessageComposer(private val context: Context) : IItemMessageComposer {
    override fun composeShareDetails(item: Item): String =
        context.getString(R.string.composed_message, item.name, String.format("%.2f", item.price))
}
