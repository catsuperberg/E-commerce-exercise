package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.item.edit

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IImagePicker
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IItemUpdater
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ItemEditViewModel(
    private val navCallbacks: IItemEditViewModel.NavCallbacks,
    private val initialItem: Item?,
    private val imagePicker: IImagePicker,
    private val updater: IItemUpdater
) : ViewModel(), IItemEditViewModel {
    override val id = initialItem?.id
    override val initialPictureUrl = initialItem?.pathDownload

    override val name: MutableStateFlow<String> = MutableStateFlow(initialItem?.name ?: "")
    override val description: MutableStateFlow<String> = MutableStateFlow(initialItem?.description ?: "")
    override val price: MutableStateFlow<String> = MutableStateFlow(
        initialItem?.price?.let { String.format("%.2f", it) } ?: ""
    )
    override val available: MutableStateFlow<Boolean> = MutableStateFlow(initialItem?.available ?: true)
    override val pictureUri: MutableStateFlow<Uri?> = MutableStateFlow(null)

    override fun onNameChange(value: String) {
        name.value = value
    }

    override fun onDescriptionChange(value: String) {
        description.value = value
    }

    override fun onPriceChange(value: String) {
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
            pictureUri.value = result.getOrNull()
        else
            Log.d("E", "изображение не выбрано")
    }

    override fun onApply() {

    }

    override fun onDelete() {
        TODO("onDelete() not implemented in ItemEditViewModel")
    }
}
