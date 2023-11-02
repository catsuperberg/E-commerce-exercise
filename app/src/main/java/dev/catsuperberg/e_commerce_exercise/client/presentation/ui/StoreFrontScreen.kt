package dev.catsuperberg.e_commerce_exercise.client.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
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
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
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
        topBar = { StoreFrontAppBar(stringResource(R.string.store_name), scrollBehavior, viewModel::onAuthScreen) }
    ) { innerPadding ->
        val pagingItems = viewModel.items.collectAsLazyPagingItems()
        val selectedItem = viewModel.selectedItem.collectAsState()
        val refresh = pagingItems.loadState.refresh
        val append = pagingItems.loadState.append

        val placeholderImage = painterResource(R.drawable.ic_item_placeholder_background)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), start = 16.dp, end = 16.dp)
        ) {
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey(),
                contentType = pagingItems.itemContentType()
            ) { index ->
                val item = pagingItems[index]
                item?.also {AppropriateCard(index, selectedItem, it, placeholderImage, viewModel) }
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

@Composable
private fun AppropriateCard(
    index: Int,
    selectedItem: State<Int?>,
    it: Item,
    placeholderImage: Painter,
    viewModel: IStoreFrontViewModel
) {
    if (index != selectedItem.value)
        ItemCard(item = it, index, placeholderImage, viewModel)
    else
        FullItemCard(item = it, index, placeholderImage, viewModel)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun StoreFrontAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onAuth: () -> Unit
) {
    MediumTopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
            navigationIconContentColor = MaterialTheme.colorScheme.surfaceVariant,
            actionIconContentColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        title = {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_store_logo),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        },
        actions = {
            IconButton(onClick = onAuth) {
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
