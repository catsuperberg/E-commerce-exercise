package dev.catsuperberg.e_commerce_exercise.client.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import dev.catsuperberg.e_commerce_exercise.client.R
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.common.ProgressIndicator
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.components.FullItemCard
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.components.ItemCard
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.store.front.IStoreFrontViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreFrontScreen(viewModel: IStoreFrontViewModel) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.tertiary,
                    actionIconContentColor = MaterialTheme.colorScheme.tertiary,
                    titleContentColor = MaterialTheme.colorScheme.tertiary
                ),
                title = {
                    Text(
                        text = stringResource(R.string.store_name).uppercase(),
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_store_logo), contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                },
                actions = {
                    IconButton(onClick = viewModel::onAuthScreen) {
                        Icon(
                            imageVector = Icons.Filled.AccountBox,
                            contentDescription = stringResource(R.string.authentication),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                windowInsets = WindowInsets(left = 8.dp, right = 8.dp, top = 4.dp, bottom = 4.dp),
            )
        }
    ) { innerPadding ->
        val pagingItems = viewModel.items.collectAsLazyPagingItems()
        val selectedItem = viewModel.selectedItem.collectAsState()
        val refresh = pagingItems.loadState.refresh
        val append = pagingItems.loadState.append

        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                items(
                    count = pagingItems.itemCount,
                    key = pagingItems.itemKey(),
                    contentType = pagingItems.itemContentType()
                ) { index ->
                    val item = pagingItems[index]
                    item?.also {
                        if (index == selectedItem.value)
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
}
