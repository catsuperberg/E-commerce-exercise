package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.order.form

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.transformation.PhoneVisualTransformation
import kotlinx.coroutines.flow.MutableStateFlow

class OrderFormViewModel(item: Item) : ViewModel(), IOrderFormViewModel {
    override val customerName: MutableStateFlow<String> = MutableStateFlow("")
    override val customerPhone: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue(""))
    override val customerEmail: MutableStateFlow<String> = MutableStateFlow("")

    override val itemName: String = item.name
    override val itemPrice: String = String.format("%.2f", item.price)
    override val itemId: String = item.id

    override fun onNameChange(value: String) {
        customerName.value = value
    }

    override fun onPhoneChange(value: TextFieldValue) {
        if (PhoneVisualTransformation.isPartialPhone(value.text))
            customerPhone.value = value
    }

    override fun onEmailChange(value: String) {
        customerEmail.value = value
    }

    override fun onSendOrder() {
        TODO("Not yet implemented")
    }
}
