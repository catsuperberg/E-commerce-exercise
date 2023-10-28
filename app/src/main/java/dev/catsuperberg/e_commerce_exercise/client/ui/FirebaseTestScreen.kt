package dev.catsuperberg.e_commerce_exercise.client.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.catsuperberg.e_commerce_exercise.client.view.model.FirebaseTestViewModel

@Composable
fun FirebaseTestScreen(viewModel: FirebaseTestViewModel) {
    val items = viewModel.items.collectAsState()

    Column {
        Text(text = "Firebase Test Screen", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(24.dp))
        Column {
            items.value.forEach {
                Text(text = it)
            }
        }
    }
}
