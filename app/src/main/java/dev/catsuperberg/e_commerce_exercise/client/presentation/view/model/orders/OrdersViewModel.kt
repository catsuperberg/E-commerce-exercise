package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Order
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IPaginatedOrderProvider

class OrdersViewModel(
    private val navCallbacks: IOrdersViewModel.NavCallbacks,
    orderProvider: IPaginatedOrderProvider
) : ViewModel(), IOrdersViewModel {
    override val orders = orderProvider.createOrderPagerFlow().cachedIn(viewModelScope)

    override fun onFulfill(order: Order) {
        TODO("Not yet implemented")
    }

    override fun onCancel(order: Order) {
        TODO("Not yet implemented")
    }

    override fun onBack() {
        navCallbacks.onBack()
    }
}
