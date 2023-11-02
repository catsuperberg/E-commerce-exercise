package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Order
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IOrderUpdater
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IPaginatedOrderProvider

class OrdersViewModel(
    private val navCallbacks: IOrdersViewModel.NavCallbacks,
    private val orderUpdater: IOrderUpdater,
    orderProvider: IPaginatedOrderProvider
) : ViewModel(), IOrdersViewModel {
    override val orders = orderProvider.createOrderPagerFlow().cachedIn(viewModelScope)

    override fun onFulfill(order: Order) {
        order.id?.also(orderUpdater::fulfill)
    }

    override fun onCancel(order: Order) {
        order.id?.also(orderUpdater::cancel)
    }

    override fun onBack() {
        navCallbacks.onBack()
    }
}
