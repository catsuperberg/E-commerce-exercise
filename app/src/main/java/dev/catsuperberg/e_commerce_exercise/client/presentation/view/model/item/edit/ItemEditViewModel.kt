package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.item.edit

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.catsuperberg.e_commerce_exercise.client.R
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.model.NewItem
import dev.catsuperberg.e_commerce_exercise.client.domain.service.UiText.StringResource
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IImagePicker
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IItemUpdater
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ItemEditViewModel(
    private val navCallbacks: IItemEditViewModel.NavCallbacks,
    private val initialItem: Item?,
    private val imagePicker: IImagePicker,
    private val updater: IItemUpdater
) : ViewModel(), IItemEditViewModel {
    private val priceRegex = Regex("^(?:[1-9]\\d*|0)?(?:\\.\\d+)?\$")

    override val id = initialItem?.id

    private val _name: MutableStateFlow<String> = MutableStateFlow(initialItem?.name ?: "")
    override val name: StateFlow<String> = _name.asStateFlow()
    private val _description: MutableStateFlow<String> = MutableStateFlow(initialItem?.description ?: "")
    override val description: StateFlow<String> = _description.asStateFlow()
    private val _price: MutableStateFlow<String> = MutableStateFlow(
        initialItem?.price?.let { String.format("%.2f", it) } ?: ""
    )
    override val price: StateFlow<String> = _price.asStateFlow()
    private val _available: MutableStateFlow<Boolean> = MutableStateFlow(initialItem?.available ?: true)
    override val available: StateFlow<Boolean> = _available.asStateFlow()

    private val _nameInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val nameInvalid: StateFlow<Boolean> = _nameInvalid.asStateFlow()
    private val _priceInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val priceInvalid: StateFlow<Boolean> = _priceInvalid.asStateFlow()

    private val _imageUri: MutableStateFlow<Uri?> = MutableStateFlow(initialItem?.pathDownload?.toUri())
    override val imageUri: StateFlow<Uri?> = _imageUri.asStateFlow()

    override val snackBarMessage: MutableSharedFlow<StringResource> = MutableSharedFlow(1)

    private var storeJob: Job? = null

    override fun onNameChange(value: String) {
        _nameInvalid.value = false
        _name.value = value
    }

    override fun onDescriptionChange(value: String) {
        _description.value = value
    }

    override fun onPriceChange(value: String) {
        _priceInvalid.value = false
        _price.value = value
    }

    override fun onAvailableChange(value: Boolean) {
        _available.value = value
    }

    override fun onPickImage() {
        viewModelScope.launch {
            pickImage()
        }
    }

    private suspend fun pickImage() {
        val result = imagePicker.pickImage()
        if(result.isSuccess)
            _imageUri.value = result.getOrNull()
        else
            snackBarMessage.emit(StringResource(R.string.no_picture_selected))
    }

    override fun onApply() {
        highlightInvalidValues()
        if(inputsInvalid())
            return
        if(storeJob != null && storeJob!!.isCompleted.not())
            return

        storeJob = viewModelScope.launch {
            store()
        }
    }

    private suspend fun store() {
        _imageUri.value?.also { image ->
            val result = initialItem?.let { initial ->
                val newImage = if(image != initial.pathDownload?.toUri()) image else null
                updater.updateItem(
                    item = initial.copy(
                        name = name.value,
                        description = description.value,
                        price = price.value.toFloat(),
                        available = available.value,
                    ),
                    imageUri = newImage
                )
            } ?: updater.storeItem(
                    item = NewItem(
                        name = name.value,
                        description = description.value,
                        price = price.value.toFloat(),
                        available = available.value
                    ),
                    imageUri = image
                )

            if(result.isSuccess)
                navCallbacks.onBack()
            else
                snackBarMessage.emit(StringResource(R.string.could_not_upload_item))
        } ?: snackBarMessage.emit(StringResource(R.string.no_picture_selected))
    }

    override fun onDelete() {
        initialItem?.let { initial ->
            viewModelScope.launch { dispose(initial.id) }
        }
    }

    override fun onBack() {
        navCallbacks.onBack()
    }

    private suspend fun dispose(id: String) {
        val result = updater.disposeOfItem(id)
        if(result.isSuccess)
            navCallbacks.onBack()
        else
            snackBarMessage.emit(StringResource(R.string.could_not_delete_item))
    }

    private fun inputsInvalid() = nameInvalid.value || priceInvalid.value

    private fun highlightInvalidValues() {
        _nameInvalid.value = name.value.isBlank()
        _priceInvalid.value = price.value.isBlank() || priceRegex.containsMatchIn(price.value).not()
    }
}
