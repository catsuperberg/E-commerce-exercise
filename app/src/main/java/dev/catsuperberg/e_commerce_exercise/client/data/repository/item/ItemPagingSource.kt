package dev.catsuperberg.e_commerce_exercise.client.data.repository.item

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import kotlinx.coroutines.tasks.await

class ItemPagingSource(
    private val queryItems: Query,
    private val itemMapper: IItemMapper
) : PagingSource<QuerySnapshot, Item>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, Item>): QuerySnapshot? = null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Item> {
        return try {
            val currentPage = params.key ?: queryItems.get().await()
            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
            val nextPage = queryItems.startAfter(lastVisibleProduct).get().await()
            LoadResult.Page(
                data = currentPage.mapNotNull(itemMapper::map),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            Log.e("E", LoadResult.Error<QuerySnapshot, Item>(e).toString());
            LoadResult.Error(e)
        }
    }

    companion object {
        const val pageSize = 5

        val itemQuery = FirebaseFirestore.getInstance()
            .collection(ItemSchema.collectionName)
            .orderBy(ItemSchema.name, Query.Direction.ASCENDING)
            .limit(pageSize.toLong())

        val availableItemQuery = FirebaseFirestore.getInstance()
            .collection(ItemSchema.collectionName)
            .orderBy(ItemSchema.name, Query.Direction.ASCENDING)
            .whereEqualTo(ItemSchema.available, true)
            .limit(pageSize.toLong())
    }
}
