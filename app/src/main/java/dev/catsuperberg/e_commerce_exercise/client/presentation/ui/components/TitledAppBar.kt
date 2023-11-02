package dev.catsuperberg.e_commerce_exercise.client.presentation.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TitledAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onBack: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.tertiary,
            actionIconContentColor = MaterialTheme.colorScheme.tertiary,
            titleContentColor = MaterialTheme.colorScheme.tertiary
        ),
        title = {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        scrollBehavior = scrollBehavior,
        windowInsets = WindowInsets(left = 8.dp, right = 8.dp, top = 4.dp, bottom = 4.dp),
    )
}
