package dev.catsuperberg.e_commerce_exercise.client.domain.usecase

import androidx.paging.PagingData
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface IPaginatedItemProvider {
    fun createItemPagerFlow(): Flow<PagingData<Item>>
    fun createAvailableItemPagerFlow(): Flow<PagingData<Item>>
}
