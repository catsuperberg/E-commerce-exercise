package dev.catsuperberg.e_commerce_exercise.client.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
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
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.components.ManagerItemCard
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.manager.store.front.IManagerStoreFrontViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerStoreFrontScreen(viewModel: IManagerStoreFrontViewModel) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.tertiary,
                    actionIconContentColor = MaterialTheme.colorScheme.tertiary,
                    titleContentColor = MaterialTheme.colorScheme.tertiary
                ),
                title = {
                    Text(
                        text = stringResource(R.string.manager_store_front).uppercase(),
                        style = MaterialTheme.typography.titleMedium,
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
                    IconButton(onClick = viewModel::onEditItem) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.create_item),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    IconButton(onClick = viewModel::onOrdersScreen) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Assignment,
                            contentDescription = stringResource(R.string.orders),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    IconButton(onClick = viewModel::onSignOut) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = stringResource(R.string.sign_out),
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
                    item?.also { ManagerItemCard(item = it, onEdit = viewModel::onEditItem) }
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
