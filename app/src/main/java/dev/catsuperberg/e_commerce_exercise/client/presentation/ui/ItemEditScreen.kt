package dev.catsuperberg.e_commerce_exercise.client.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.catsuperberg.e_commerce_exercise.client.R
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.item.edit.IItemEditViewModel

@Composable
fun ItemEditScreen(viewModel: IItemEditViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 48.dp, horizontal = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(viewModel, Modifier.weight(0.45f))
        ItemDetailInputs(viewModel, modifier = Modifier.weight(1f))

        Button(
            onClick = viewModel::onApply,
            shape = MaterialTheme.shapes.large,
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxWidth(),
        ){
            Text(
                text = stringResource(R.string.store_item).uppercase(),
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}

@Composable
private fun Image(viewModel: IItemEditViewModel, modifier: Modifier) {
    val pickedUri = viewModel.imageUri.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = pickedUri.value,
            contentDescription = stringResource(
                R.string.picture_content_description,
                stringResource(R.string.new_item_image)
            ),
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp)),
        )
        Button(
            onClick = viewModel::onPickImage,
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(48.dp)
                .aspectRatio(1f)
        ) {
            Icon(
                imageVector = Icons.Filled.Photo,
                contentDescription = stringResource(R.string.pick_image),
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun ItemDetailInputs(viewModel: IItemEditViewModel, modifier: Modifier) {
    val nameString = viewModel.name.collectAsState()
    val descriptionString = viewModel.description.collectAsState()
    val priceString = viewModel.price.collectAsState()
    val availableState = viewModel.available.collectAsState()

    val nameError = viewModel.nameInvalid.collectAsState()
    val priceError = viewModel.priceInvalid.collectAsState()

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(
                R.string.item_id_display,
                viewModel.id ?: stringResource(R.string.auto_generation)
            ),
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            value = nameString.value,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            label = { Text(stringResource(R.string.item_name)) },
            shape = MaterialTheme.shapes.small,
            onValueChange = viewModel::onNameChange,
            modifier = Modifier
                .fillMaxWidth(),
            isError = nameError.value
        )

        OutlinedTextField(
            value = descriptionString.value,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            label = { Text(stringResource(R.string.item_description)) },
            shape = MaterialTheme.shapes.small,
            onValueChange = viewModel::onDescriptionChange,
            modifier = Modifier
                .fillMaxWidth(),
        )

        OutlinedTextField(
            value = priceString.value,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.clearFocus() }
            ),
            label = { Text(stringResource(R.string.item_price)) },
            shape = MaterialTheme.shapes.small,
            onValueChange = viewModel::onPriceChange,
            modifier = Modifier
                .fillMaxWidth(),
            isError = priceError.value
        )


        Row {
            Checkbox(
                checked = availableState.value,
                onCheckedChange = viewModel::onAvailableChange
            )
            Text(
                text = stringResource(R.string.item_available),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
