package dev.catsuperberg.e_commerce_exercise.client.data.repository.order

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Order
import kotlinx.coroutines.tasks.await

class OrderPagingSource(
    private val queryItems: Query,
    private val orderMapper: IOrderMapper
) : PagingSource<QuerySnapshot, Order>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, Order>): QuerySnapshot? = null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Order> {
        return try {
            val currentPage = params.key ?: queryItems.get().await()
            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
            val nextPage = queryItems.startAfter(lastVisibleProduct).get().await()
            LoadResult.Page(
                data = currentPage.mapNotNull(orderMapper::map),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            Log.e("E", LoadResult.Error<QuerySnapshot, Order>(e).toString())
            LoadResult.Error(e)
        }
    }

    companion object {
        const val pageSize = 15

        val orderQuery = FirebaseFirestore.getInstance()
            .collection(OrderSchema.collectionName)
            .orderBy(OrderSchema.created, Query.Direction.DESCENDING)
            .limit(pageSize.toLong())
    }
}
