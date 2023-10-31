package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IItemDetailsSender
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IPaginatedItemProvider
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel(
    private val navCallbacks: IMainViewModel.NavCallbacks,
    private val detailsSender: IItemDetailsSender,
    itemProvider: IPaginatedItemProvider
) : ViewModel(), IMainViewModel {
    override val items = itemProvider.createItemPagerFlow().cachedIn(viewModelScope)
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
