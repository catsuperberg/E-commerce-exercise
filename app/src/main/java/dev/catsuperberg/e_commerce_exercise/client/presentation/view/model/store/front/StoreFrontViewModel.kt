package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.store.front

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IItemDetailsSender
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IPaginatedItemProvider
import kotlinx.coroutines.flow.MutableStateFlow

class StoreFrontViewModel(
    private val navCallbacks: IStoreFrontViewModel.NavCallbacks,
    private val detailsSender: IItemDetailsSender,
    itemProvider: IPaginatedItemProvider
) : ViewModel(), IStoreFrontViewModel {
    override val items = itemProvider.createAvailableItemPagerFlow().cachedIn(viewModelScope)
    override val selectedItem: MutableStateFlow<Int?> = MutableStateFlow(null)

    override fun onAuthScreen() {
        navCallbacks.onAuth()
    }

    override fun onCardClick(index: Int) {
        if(selectedItem.value == index)
            selectedItem.value = null
        else
            selectedItem.value = index
    }

    override fun onBuy(item: Item) {
        navCallbacks.onBuyItem(item)
    }

    override fun onShare(item: Item) {
        detailsSender.sendToMessenger(item)
    }
}
