package dev.catsuperberg.e_commerce_exercise.client.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.catsuperberg.e_commerce_exercise.client.R
import dev.catsuperberg.e_commerce_exercise.client.view.model.FirebaseTestViewModel

@Composable
fun FirebaseTestScreen(viewModel: FirebaseTestViewModel) {
    val items = viewModel.items.collectAsState()

    Column {
        Text(text = "Firebase Test Screen", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(24.dp))
        Column {
            items.value.forEach {item ->
                Row(
                    modifier = Modifier.height(84.dp)
                ) {
                    AsyncImage(
                        model = item.imagePath,
                        placeholder = painterResource(R.drawable.ic_launcher_foreground),
                        fallback = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = "Item picture",
                        modifier = Modifier.fillMaxHeight().aspectRatio(1f).padding(8.dp)
                    )
                    item.name?.let { Text(text = it) }
                }
            }
        }
    }
}
