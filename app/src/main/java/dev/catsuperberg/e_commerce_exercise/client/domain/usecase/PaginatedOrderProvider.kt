package dev.catsuperberg.e_commerce_exercise.client.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.google.firebase.firestore.QuerySnapshot
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Order
import kotlinx.coroutines.flow.Flow

class PaginatedOrderProvider(
    private val orderSource: PagingSource<QuerySnapshot, Order>,
    private val config: PagingConfig
) : IPaginatedOrderProvider {
    override fun createOrderPagerFlow(): Flow<PagingData<Order>> = Pager(config = config) { orderSource }.flow
}
