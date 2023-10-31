package dev.catsuperberg.e_commerce_exercise.client.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.catsuperberg.e_commerce_exercise.client.R
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.transformation.PhoneVisualTransformation
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.order.form.IOrderFormViewModel

@Composable
fun OrderFormScreen(viewModel: IOrderFormViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 48.dp, horizontal = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CustomerContactInfoInputs(viewModel, modifier = Modifier.weight(1f))

        Column (verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ItemDetailsReminder(viewModel.itemName, viewModel.itemPrice, viewModel.itemId, Modifier.padding(16.dp))
            Button(
                onClick = { viewModel.onSendOrder() },
                shape = MaterialTheme.shapes.large,
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.buy).uppercase(),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CustomerContactInfoInputs(viewModel: IOrderFormViewModel, modifier: Modifier) {
    val nameString = viewModel.customerName.collectAsState()
    val phoneString = viewModel.customerPhone.collectAsState()
    val emailString = viewModel.customerEmail.collectAsState()

    val focusManager = LocalFocusManager.current
    val phoneTransformation = remember { PhoneVisualTransformation() }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = nameString.value,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            label = { Text(stringResource(R.string.customer_name)) },
            shape = MaterialTheme.shapes.small,
            onValueChange = viewModel::onNameChange,
            modifier = Modifier
                .fillMaxWidth(),
        )

        OutlinedTextField(
            value = phoneString.value,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            label = { Text(stringResource(R.string.phone_number)) },
            shape = MaterialTheme.shapes.small,
            onValueChange = viewModel::onPhoneChange,
            visualTransformation = phoneTransformation,
            modifier = Modifier
                .fillMaxWidth(),
        )

        OutlinedTextField(
            value = emailString.value,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.clearFocus() }
            ),
            label = { Text(stringResource(R.string.customer_email)) },
            shape = MaterialTheme.shapes.small,
            onValueChange = viewModel::onEmailChange,
            modifier = Modifier
                .fillMaxWidth(),
        )
        
        Text(
            text = "Пожалуйста заполните контактные данные для оформления заказа",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ItemDetailsReminder(name: String, price: String, id: String, modifier: Modifier) {
    Text(
        text = stringResource(R.string.item_details_reminder, name, id, price).trimMargin(),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Left,
        modifier = modifier.fillMaxWidth()
    )
}
