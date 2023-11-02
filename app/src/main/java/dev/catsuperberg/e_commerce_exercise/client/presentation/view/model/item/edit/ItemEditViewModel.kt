package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.item.edit

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.model.NewItem
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IImagePicker
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IItemUpdater
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ItemEditViewModel(
    private val navCallbacks: IItemEditViewModel.NavCallbacks,
    private val initialItem: Item?,
    private val imagePicker: IImagePicker,
    private val updater: IItemUpdater
) : ViewModel(), IItemEditViewModel {
    private val priceRegex = Regex("^(?:[1-9]\\d*|0)?(?:\\.\\d+)?\$")

    override val id = initialItem?.id

    override val name: MutableStateFlow<String> = MutableStateFlow(initialItem?.name ?: "")
    override val description: MutableStateFlow<String> = MutableStateFlow(initialItem?.description ?: "")
    override val price: MutableStateFlow<String> = MutableStateFlow(
        initialItem?.price?.let { String.format("%.2f", it) } ?: ""
    )
    override val available: MutableStateFlow<Boolean> = MutableStateFlow(initialItem?.available ?: true)

    override val nameInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val priceInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val imageUri: MutableStateFlow<Uri?> = MutableStateFlow(initialItem?.pathDownload?.toUri())

    private var storeJob: Job? = null

    override fun onNameChange(value: String) {
        nameInvalid.value = false
        name.value = value
    }

    override fun onDescriptionChange(value: String) {
        description.value = value
    }

    override fun onPriceChange(value: String) {
        priceInvalid.value = false
        price.value = value
    }

    override fun onAvailableChange(value: Boolean) {
        available.value = value
    }

    override fun onPickImage() {
        viewModelScope.launch {
            pickImage()
        }
    }

    private suspend fun pickImage() {
        val result = imagePicker.pickImage()
        if(result.isSuccess)
            imageUri.value = result.getOrNull()
        else
            Log.d("E", "изображение не выбрано")
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
        imageUri.value?.also { image ->
            initialItem?.also { initial ->
                val newImage = if(image != initialItem?.pathDownload?.toUri()) image else null
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
        } ?: Log.d("E", "не выбрано изображение товара")
    }

    override fun onDelete() {
        TODO("onDelete() not implemented in ItemEditViewModel")
    }

    private fun inputsInvalid() = nameInvalid.value || priceInvalid.value

    private fun highlightInvalidValues() {
        nameInvalid.value = name.value.isBlank()
        priceInvalid.value = price.value.isBlank() || priceRegex.containsMatchIn(price.value).not()
    }
}
