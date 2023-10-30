package dev.catsuperberg.e_commerce_exercise.client.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.common.ProgressIndicator
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.main.IMainViewModel

@Composable
fun MainScreen(viewModel: IMainViewModel) {
    val pagingItems = viewModel.items.collectAsLazyPagingItems()
    val selectedItem = viewModel.selectedItem.collectAsState()
    val refresh = pagingItems.loadState.refresh
    val append = pagingItems.loadState.append

    Column {
        Text(text = "Main E-commerce Screen", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn {
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey(),
                contentType = pagingItems.itemContentType()
            ) { index ->
                val item = pagingItems[index]
                item?.also {
                    if(index == selectedItem.value)
                        FullItemCard(item = it, index, viewModel)
                    else
                        ItemCard(item = it, index, viewModel)
                }
            }
        }

        pagingItems.loadState.apply {
            when {
                refresh is LoadState.Loading -> ProgressIndicator()
                refresh is LoadState.Error -> Log.d("E", refresh.toString())
                append is LoadState.Loading -> ProgressIndicator()
                append is LoadState.Error -> Log.d("E", append.toString())
            }
        }
    }
}
