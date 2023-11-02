package dev.catsuperberg.e_commerce_exercise.client.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.catsuperberg.e_commerce_exercise.client.R
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.store.front.IStoreFrontViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullItemCard(item: Item, index: Int, placeholderPainter: Painter, viewModel: IStoreFrontViewModel) {
    Card(
        onClick = { viewModel.onCardClick(index) },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = item.pathDownload,
                placeholder = placeholderPainter,
                fallback = placeholderPainter,
                contentDescription = stringResource(R.string.picture_content_description, item.name),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
            )
            ItemDetails(item)
            Buttons(item, viewModel::onShare, viewModel::onBuy)
        }
    }
}

@Composable
private fun Buttons(
    item: Item,
    onShare: (Item) -> Unit,
    onBuy: (Item) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Box(modifier = Modifier.weight(0.35f)) {
            Button(
                onClick = { onShare(item) },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .aspectRatio(1f, true)
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = null,
                )
            }
        }
        Button(
            onClick = { onBuy(item) },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.65f),
        ) {
            Text(
                text = stringResource(R.string.buy).uppercase(),
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}

@Composable
private fun ItemDetails(item: Item) {
    Column {
        Text(
            text = item.name,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )

        item.description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(0.35f))
            Column(
                modifier = Modifier
                    .weight(0.65f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = String.format("%.2f", item.price),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider()
            }
        }

    }
}
