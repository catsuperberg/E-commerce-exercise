package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.item.edit

import androidx.lifecycle.ViewModel
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IItemUpdater
import kotlinx.coroutines.flow.MutableStateFlow

class ItemEditViewModel(
    private val navCallbacks: IItemEditViewModel.NavCallbacks,
    private val initialItem: Item?,
    private val updater: IItemUpdater
) : ViewModel(), IItemEditViewModel {
    override val id = initialItem?.id

    override val name: MutableStateFlow<String> = MutableStateFlow(initialItem?.name ?: "")
    override val description: MutableStateFlow<String> = MutableStateFlow(initialItem?.description ?: "")
    override val price: MutableStateFlow<String> = MutableStateFlow(
        initialItem?.price?.let { String.format("%.2f", it) } ?: ""
    )
    override val available: MutableStateFlow<Boolean> = MutableStateFlow(initialItem?.available ?: true)

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
    }

    override fun onApply() {

    }

    override fun onDelete() {
        TODO("onDelete() not implemented in ItemEditViewModel")
    }
}
