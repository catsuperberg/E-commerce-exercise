package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.item.edit

import kotlinx.coroutines.flow.StateFlow

interface IItemEditViewModel {
    val id: String?
    val name: StateFlow<String>
    val description: StateFlow<String>
    val price: StateFlow<String>
    val available: StateFlow<Boolean>

    fun onNameChange(value: String)
    fun onDescriptionChange(value: String)
    fun onPriceChange(value: String)
    fun onAvailableChange(value: Boolean)

    fun onPickImage()
    fun onApply()
    fun onDelete()

    data class NavCallbacks(
        val onSuccess: () -> Unit,
    )
}
