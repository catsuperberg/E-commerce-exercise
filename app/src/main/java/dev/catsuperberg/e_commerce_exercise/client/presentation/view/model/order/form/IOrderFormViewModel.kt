package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.order.form

import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.flow.StateFlow

interface IOrderFormViewModel {
    val customerName: StateFlow<String>
    val customerPhone: StateFlow<TextFieldValue>
    val customerEmail: StateFlow<String>

    val customerNameInvalid: StateFlow<Boolean>
    val customerPhoneInvalid: StateFlow<Boolean>
    val customerEmailInvalid: StateFlow<Boolean>

    val itemName: String
    val itemPrice: String
    val itemId: String

    fun onNameChange(value: String)
    fun onPhoneChange(value: TextFieldValue)
    fun onEmailChange(value: String)

    fun onSendOrder()
    fun onBack()

    data class NavCallbacks(
        val onOrderOpened: () -> Unit,
        val onBack: () -> Unit,
    )
}
