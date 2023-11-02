package dev.catsuperberg.e_commerce_exercise.client.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.catsuperberg.e_commerce_exercise.client.R
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.store.front.IStoreFrontViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(item: Item, index: Int, placeholderPainter: Painter, viewModel: IStoreFrontViewModel) {
    Card(
        onClick = { viewModel.onCardClick(index) },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3f)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize().padding(12.dp)
        ) {
            AsyncImage(
                model = item.pathDownload,
                placeholder = placeholderPainter,
                fallback = placeholderPainter,
                contentDescription = stringResource(R.string.picture_content_description, item.name),
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(min = 64.dp, max = 192.dp)
                    .clip(RoundedCornerShape(12.dp)),
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(min = 192.dp, max = 216.dp),
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Right,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false,
                    modifier = Modifier.fillMaxWidth()
                )

                Surface(
                    shape = MaterialTheme.shapes.large,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.widthIn(min = 64.dp, max = 96.dp),
                ) {
                    Text(
                        text = String.format("%.2f", item.price),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp).wrapContentHeight()
                    )
                }
            }
        }
    }
}
