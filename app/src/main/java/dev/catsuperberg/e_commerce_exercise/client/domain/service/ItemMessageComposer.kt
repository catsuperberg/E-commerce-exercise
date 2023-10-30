package dev.catsuperberg.e_commerce_exercise.client.domain.service

import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item

class ItemMessageComposer : IItemMessageComposer {
    override fun composeShareDetails(item: Item): String {
        return """
            Эй, посмотри на это нечто которое я нашёл в новом магазине привидений.
            Это ${item.name} и стоит всего ${item.price} забытых завтраков.
        """.trimIndent()
    }
}
