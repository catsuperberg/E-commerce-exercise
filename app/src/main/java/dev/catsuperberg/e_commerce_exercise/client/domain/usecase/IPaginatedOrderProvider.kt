package dev.catsuperberg.e_commerce_exercise.client.domain.usecase

import androidx.paging.PagingData
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface IPaginatedOrderProvider {
    fun createOrderPagerFlow(): Flow<PagingData<Order>>
}
