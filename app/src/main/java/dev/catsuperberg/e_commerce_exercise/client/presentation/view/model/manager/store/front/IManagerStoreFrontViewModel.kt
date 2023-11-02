package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.manager.store.front

import androidx.paging.PagingData
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface IManagerStoreFrontViewModel {
    val items: Flow<PagingData<Item>>

    fun onOrdersScreen()
    fun onSignOut()

    fun onEditItem(item: Item? = null)

    data class NavCallbacks(
        val onOrdersScreen: () -> Unit,
        val onEditItem: (item: Item?) -> Unit
    )
}
