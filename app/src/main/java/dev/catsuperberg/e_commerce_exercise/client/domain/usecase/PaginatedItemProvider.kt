package dev.catsuperberg.e_commerce_exercise.client.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.google.firebase.firestore.QuerySnapshot
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import kotlinx.coroutines.flow.Flow

class PaginatedItemProvider(
    private val itemSource: PagingSource<QuerySnapshot, Item>,
    private val availableItemSource: PagingSource<QuerySnapshot, Item>,
    private val config: PagingConfig
) : IPaginatedItemProvider {
    override fun createItemPagerFlow(): Flow<PagingData<Item>> = Pager(config = config) { itemSource }.flow
    override fun createAvailableItemPagerFlow(): Flow<PagingData<Item>> =
        Pager(config = config) { availableItemSource }.flow
}
