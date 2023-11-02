package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.orders

import androidx.paging.PagingData
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface IOrdersViewModel {
    val orders: Flow<PagingData<Order>>

    fun onFulfill(order: Order)
    fun onCancel(order: Order)
    fun onBack()

    data class NavCallbacks(
        val onBack: () -> Unit,
    )
}
