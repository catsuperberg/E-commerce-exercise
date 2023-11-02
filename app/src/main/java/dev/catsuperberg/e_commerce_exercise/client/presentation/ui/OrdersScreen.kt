package dev.catsuperberg.e_commerce_exercise.client.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import dev.catsuperberg.e_commerce_exercise.client.R
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.common.ProgressIndicator
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.components.OrderCard
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.components.TitledAppBar
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.orders.IOrdersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(viewModel: IOrdersViewModel) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TitledAppBar(stringResource(R.string.order_management), scrollBehavior, viewModel::onBack) }
    ) { innerPadding ->
        val pagingOrders = viewModel.orders.collectAsLazyPagingItems()
        val refresh = pagingOrders.loadState.refresh
        val append = pagingOrders.loadState.append

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding(), start = 16.dp, end = 16.dp)
        ) {
            items(
                count = pagingOrders.itemCount,
                key = pagingOrders.itemKey(),
                contentType = pagingOrders.itemContentType()
            ) { index ->
                val order = pagingOrders[index]
                order?.also { OrderCard(it, viewModel::onFulfill, viewModel::onCancel) }
            }
        }

        pagingOrders.loadState.apply {
            when {
                refresh is LoadState.Loading -> ProgressIndicator()
                refresh is LoadState.Error -> Log.d("E", refresh.toString())
                append is LoadState.Loading -> ProgressIndicator()
                append is LoadState.Error -> Log.d("E", append.toString())
            }
        }
    }
}
