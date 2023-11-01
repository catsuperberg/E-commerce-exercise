package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.store.front

import androidx.paging.PagingData
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IStoreFrontViewModel {
    val items: Flow<PagingData<Item>>
    val selectedItem: StateFlow<Int?>

    fun onAuthScreen()

    fun onCardClick(index: Int)
    fun onBuy(item: Item)
    fun onShare(item: Item)

    data class NavCallbacks(
        val onBuyItem: (item: Item) -> Unit,
        val onAuth: () -> Unit,
    )
}
