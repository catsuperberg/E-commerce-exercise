package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.order.form

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.catsuperberg.e_commerce_exercise.client.R
import dev.catsuperberg.e_commerce_exercise.client.domain.model.CustomerContactDetails
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.service.UiText.StringResource
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IOrderRegistration
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.transformation.PhoneVisualTransformation
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OrderFormViewModel(
    private val navCallbacks: IOrderFormViewModel.NavCallbacks,
    private val item: Item,
    private val orderRegistration: IOrderRegistration
) : ViewModel(), IOrderFormViewModel {
    override val customerName: MutableStateFlow<String> = MutableStateFlow("")
    override val customerPhone: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue(""))
    override val customerEmail: MutableStateFlow<String> = MutableStateFlow("")

    override val customerNameInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val customerPhoneInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val customerEmailInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val itemName: String = item.name
    override val itemPrice: String = String.format("%.2f", item.price)
    override val itemId: String = item.id

    override val snackBarMessage: MutableSharedFlow<StringResource> = MutableSharedFlow(1)

    private var orderJob: Job? = null

    override fun onNameChange(value: String) {
        customerNameInvalid.value = false
        customerName.value = value
    }

    override fun onPhoneChange(value: TextFieldValue) {
        customerPhoneInvalid.value = false
        if (PhoneVisualTransformation.isPartialPhone(value.text))
            customerPhone.value = value
    }

    override fun onEmailChange(value: String) {
        customerEmailInvalid.value = false
        customerEmail.value = value
    }

    override fun onSendOrder() {
        highlightInvalidValues()
        if(contactDetailsInvalid())
            return
        if(orderJob != null && orderJob!!.isCompleted.not())
            return

        orderJob = viewModelScope.launch {
            registerOrder()
        }
    }

    override fun onBack() {
        navCallbacks.onBack()
    }

    private suspend fun registerOrder() {
        val result = orderRegistration.open(
            item,
            CustomerContactDetails(customerName.value, customerPhone.value.text, customerEmail.value)
        )
        if(result.isSuccess)
            navCallbacks.onOrderOpened()
        else
            snackBarMessage.emit(StringResource(R.string.failed_order_processing))
    }

    private fun contactDetailsInvalid() =
        customerNameInvalid.value || customerPhoneInvalid.value || customerEmailInvalid.value

    private fun highlightInvalidValues() {
        customerNameInvalid.value = customerName.value.isBlank()
        customerPhoneInvalid.value = PhoneVisualTransformation.isPhone(customerPhone.value.text).not()
        customerEmailInvalid.value = customerEmail.value.let {
            it.isBlank() || android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches().not()
        }
    }
}
