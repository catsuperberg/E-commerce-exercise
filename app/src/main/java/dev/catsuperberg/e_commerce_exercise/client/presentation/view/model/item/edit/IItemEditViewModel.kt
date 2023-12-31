package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.item.edit

import android.net.Uri
import dev.catsuperberg.e_commerce_exercise.client.domain.service.UiText.StringResource
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface IItemEditViewModel {
    val id: String?
    val name: StateFlow<String>
    val description: StateFlow<String>
    val price: StateFlow<String>
    val available: StateFlow<Boolean>

    val nameInvalid: StateFlow<Boolean>
    val priceInvalid: StateFlow<Boolean>

    val imageUri: StateFlow<Uri?>

    val snackBarMessage: SharedFlow<StringResource>

    fun onNameChange(value: String)
    fun onDescriptionChange(value: String)
    fun onPriceChange(value: String)
    fun onAvailableChange(value: Boolean)

    fun onPickImage()
    fun onApply()
    fun onDelete()
    fun onBack()

    data class NavCallbacks(
        val onBack: () -> Unit
    )
}
