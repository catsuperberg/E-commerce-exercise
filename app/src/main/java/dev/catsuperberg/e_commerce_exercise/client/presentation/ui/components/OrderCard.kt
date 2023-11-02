package dev.catsuperberg.e_commerce_exercise.client.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.catsuperberg.e_commerce_exercise.client.R
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Order
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.theme.extendedColors

@Composable
fun OrderCard(order: Order, onFulfill: (Order) -> Unit, onCancel: (Order) -> Unit) {
    val cardColors = if (order.canceled) CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.outline,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    ) else if(order.fulfilled) CardDefaults.cardColors(
        containerColor = MaterialTheme.extendedColors.green,
        contentColor = MaterialTheme.extendedColors.onGreenContainer,
    ) else CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.outlineVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    val showButtons = (order.canceled || order.fulfilled).not()
    Card(
        shape = MaterialTheme.shapes.large,
        colors = cardColors,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical =  8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.75f)
            ) {
                TextFields(order)
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.25f)
            ) {
                if(showButtons) {
                    Button(
                        onClick = { onFulfill(order) },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                        )
                    }
                    Button(
                        onClick = { onCancel(order) },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TextFields(order: Order) {
    Text(
        text = order.customerName,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Black
    )
    Text(
        text = stringResource(R.string.id, order.itemId),
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Black
    )
    Text(
        text = order.customerPhone,
        style = MaterialTheme.typography.bodyMedium,
    )
    Text(
        text = order.customerEmail,
        style = MaterialTheme.typography.bodyMedium,
    )
    order.created?.also {
        val date = it.toDate()
        Text(
            text = date.toString(),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
    Text(
        text = stringResource(R.string.sum, String.format("%.2f", order.sum)),
        style = MaterialTheme.typography.bodyLarge,
    )
}
