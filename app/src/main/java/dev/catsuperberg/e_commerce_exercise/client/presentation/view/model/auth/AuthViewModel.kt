package dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.catsuperberg.e_commerce_exercise.client.R
import dev.catsuperberg.e_commerce_exercise.client.domain.service.IAccountService
import dev.catsuperberg.e_commerce_exercise.client.domain.service.UiText.StringResource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val navCallbacks: IAuthViewModel.NavCallbacks,
    private val accountService: IAccountService
) : ViewModel(), IAuthViewModel {
    private val _isSignUp: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isSignUp: StateFlow<Boolean> = _isSignUp.asStateFlow()

    private val _email: MutableStateFlow<String> = MutableStateFlow("")
    override val email: StateFlow<String> = _email.asStateFlow()
    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    override val password: StateFlow<String> = _password.asStateFlow()
    private val _repeatPassword: MutableStateFlow<String> = MutableStateFlow("")
    override val repeatPassword: StateFlow<String> = _repeatPassword.asStateFlow()

    private val _revealPasswords: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val revealPasswords: StateFlow<Boolean> = _revealPasswords.asStateFlow()

    private val _emailInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val emailInvalid: StateFlow<Boolean> = _emailInvalid.asStateFlow()
    private val _passwordInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val passwordInvalid: StateFlow<Boolean> = _passwordInvalid.asStateFlow()
    private val _repeatPasswordInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val repeatPasswordInvalid: StateFlow<Boolean> = _repeatPasswordInvalid.asStateFlow()

    override val snackBarMessage: MutableSharedFlow<StringResource> = MutableSharedFlow(1)

    private var authJob: Job? = null

    override fun onSignUpChange(value: Boolean) {
        _isSignUp.value = value
    }

    override fun onEmailChange(value: String) {
        _emailInvalid.value = false
        _email.value = value
    }

    override fun onPasswordChange(value: String) {
        _passwordInvalid.value = false
        _password.value = value
    }

    override fun onRepeatPasswordChange(value: String) {
        _repeatPasswordInvalid.value = false
        _repeatPassword.value = value
    }

    override fun onRevealPasswordsChange(value: Boolean) {
        _revealPasswords.value = value
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
            snackBarMessage.emit(StringResource(R.string.sign_in_failed))
    }

    private suspend fun singUp() {
        val result = accountService.signUp(email.value, password.value)
        if(result.isSuccess)
            navCallbacks.onBack()
        else
            snackBarMessage.emit(StringResource(R.string.sign_up_failed))
    }

    override fun onBack() {
        navCallbacks.onBack()
    }

    private fun inputsInvalid() =
        emailInvalid.value || passwordInvalid.value || (isSignUp.value && repeatPasswordInvalid.value)

    private fun highlightInvalidValues() {
        _emailInvalid.value = email.value.let {
            it.isBlank() || android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches().not()
        }
        _passwordInvalid.value = password.value.isBlank()
        _repeatPasswordInvalid.value = password.value.isBlank() || repeatPassword.value != password.value
    }
}
