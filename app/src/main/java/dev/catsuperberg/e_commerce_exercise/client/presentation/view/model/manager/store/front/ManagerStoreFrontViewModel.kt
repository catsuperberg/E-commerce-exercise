package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.manager.store.front

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.service.IAccountService
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IPaginatedItemProvider

class ManagerStoreFrontViewModel(
    private val navCallbacks: IManagerStoreFrontViewModel.NavCallbacks,
    private val accountService: IAccountService,
    itemProvider: IPaginatedItemProvider
) : ViewModel(), IManagerStoreFrontViewModel {
    override val items = itemProvider.createItemPagerFlow().cachedIn(viewModelScope)

    override fun onOrdersScreen() {
        navCallbacks.onOrdersScreen()
    }

    override fun onSignOut() {
        accountService.signOut()
    }

    override fun onEditItem(item: Item?) {
        navCallbacks.onEditItem(item)
    }
}
