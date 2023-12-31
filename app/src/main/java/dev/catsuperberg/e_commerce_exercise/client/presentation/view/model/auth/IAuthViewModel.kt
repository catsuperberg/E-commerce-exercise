package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.auth

import dev.catsuperberg.e_commerce_exercise.client.domain.service.UiText.StringResource
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface IAuthViewModel {
    val isSignUp: StateFlow<Boolean>

    val email: StateFlow<String>
    val password: StateFlow<String>
    val repeatPassword: StateFlow<String>

    val revealPasswords: StateFlow<Boolean>

    val emailInvalid: StateFlow<Boolean>
    val passwordInvalid: StateFlow<Boolean>
    val repeatPasswordInvalid: StateFlow<Boolean>

    val snackBarMessage: SharedFlow<StringResource>

    fun onSignUpChange(value: Boolean)
    fun onEmailChange(value: String)
    fun onPasswordChange(value: String)
    fun onRepeatPasswordChange(value: String)
    fun onRevealPasswordsChange(value: Boolean)
    fun onAuth()
    fun onBack()

    data class NavCallbacks(
        val onBack: () -> Unit
    )
}
