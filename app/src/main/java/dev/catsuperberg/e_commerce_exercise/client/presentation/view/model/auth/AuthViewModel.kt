package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.catsuperberg.e_commerce_exercise.client.domain.service.IAccountService
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val navCallbacks: IAuthViewModel.NavCallbacks,
    private val accountService: IAccountService
) : ViewModel(), IAuthViewModel {
    override val isSignUp: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val email: MutableStateFlow<String> = MutableStateFlow("")
    override val password: MutableStateFlow<String> = MutableStateFlow("")
    override val repeatPassword: MutableStateFlow<String> = MutableStateFlow("")

    override val revealPasswords: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val emailInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val passwordInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val repeatPasswordInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private var authJob: Job? = null

    override fun onSignUpChange(value: Boolean) {
        isSignUp.value = value
    }

    override fun onEmailChange(value: String) {
        emailInvalid.value = false
        email.value = value
    }

    override fun onPasswordChange(value: String) {
        passwordInvalid.value = false
        password.value = value
    }

    override fun onRepeatPasswordChange(value: String) {
        repeatPasswordInvalid.value = false
        repeatPassword.value = value
    }

    override fun onRevealPasswordsChange(value: Boolean) {
        revealPasswords.value = value
    }

    override fun onAuth() {
        highlightInvalidValues()
        if(inputsInvalid())
            return

        if(authJob != null && authJob!!.isCompleted.not())
            return

        authJob = viewModelScope.launch {
            if(isSignUp.value)
                singUp()
            else
                singIn()
        }
    }

    private suspend fun singIn() {
        val result = accountService.signIn(email.value, password.value)
        if(result.isSuccess)
            navCallbacks.onBack()
        else
            Log.d("E", "Sign in failed")
    }

    private suspend fun singUp() {
        val result = accountService.signUp(email.value, password.value)
        if(result.isSuccess)
            navCallbacks.onBack()
        else
            Log.d("E", "Sign up failed")
    }

    override fun onBack() {
        navCallbacks.onBack()
    }

    private fun inputsInvalid() =
        emailInvalid.value || passwordInvalid.value || (isSignUp.value && repeatPasswordInvalid.value)

    private fun highlightInvalidValues() {
        emailInvalid.value = email.value.let {
            it.isBlank() || android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches().not()
        }
        passwordInvalid.value = password.value.isBlank()
        repeatPasswordInvalid.value = password.value.isBlank() || repeatPassword.value != password.value
    }
}
