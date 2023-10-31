package dev.catsuperberg.e_commerce_exercise.client.presentation.ui

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.catsuperberg.e_commerce_exercise.client.R
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.main.IMainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullItemCard(item: Item, index: Int, viewModel: IMainViewModel) {
    Card(
        onClick = { viewModel.onCardClick(index) },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = item.pathDownload,
                placeholder = painterResource(R.drawable.ic_item_placeholder_background),
                fallback = painterResource(R.drawable.ic_item_placeholder_background),
                contentDescription = "Item picture",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clip(RoundedCornerShape(10.dp)),
            )
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
                    modifier = Modifier
                        .fillMaxWidth()
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
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Divider()
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Box(modifier = Modifier.weight(0.35f)) {
                        Button(
                            onClick = { viewModel.onShare(item) },
                            shape = CircleShape,
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.aspectRatio(1f)
                        ) {
                            Icon(
                                painterResource(R.drawable.is_share),
                                contentDescription = null,
                            )
                        }
                    }

                    Button(
                        onClick = { viewModel.onBuy(item) },
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.65f),
                    ) {
                        Text(
                            text = "Buy",
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }
            }
        }
    }
}