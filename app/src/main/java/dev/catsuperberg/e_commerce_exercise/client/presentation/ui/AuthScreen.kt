package dev.catsuperberg.e_commerce_exercise.client.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import dev.catsuperberg.e_commerce_exercise.client.R
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.components.AppSnackbar
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.components.TitledAppBar
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.auth.IAuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(viewModel: IAuthViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(true) { viewModel.snackBarMessage.collect(snackbarHostState::showSnackbar) }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data -> AppSnackbar(data) },
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 120.dp)
            )
        },
        topBar = { TitledAppBar(title = stringResource(R.string.auth), onBack = viewModel::onBack) }
    ) { innerPadding ->
        val isSignUp = viewModel.isSignUp.collectAsState()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), start = 32.dp, end = 32.dp)
        ) {
            AuthInputs(viewModel, isSignUp)

            Spacer(modifier = Modifier.height(48.dp))
            
            val authText = if (isSignUp.value) stringResource(R.string.sign_up) else stringResource(R.string.sign_in)
            Button(
                onClick = viewModel::onAuth,
                shape = MaterialTheme.shapes.large,
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(authText.uppercase(), style = MaterialTheme.typography.headlineSmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isSignUp.value, onCheckedChange = viewModel::onSignUpChange)
                Text(
                    text = stringResource(R.string.user_sign_up),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun AuthInputs(
    viewModel: IAuthViewModel,
    isSignUp: State<Boolean>
) {
    val emailString = viewModel.email.collectAsState()
    val passwordString = viewModel.password.collectAsState()
    val repeatPasswordString = viewModel.repeatPassword.collectAsState()

    val revealPassword = viewModel.revealPasswords.collectAsState()

    val emailError = viewModel.emailInvalid.collectAsState()
    val passwordError = viewModel.passwordInvalid.collectAsState()
    val repeatPasswordError = viewModel.repeatPasswordInvalid.collectAsState()


    val focusManager = LocalFocusManager.current
    val passwordTransformationInstance = remember { PasswordVisualTransformation() }
    OutlinedTextField(
        value = emailString.value,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        label = { Text(stringResource(R.string.customer_email)) },
        shape = MaterialTheme.shapes.small,
        onValueChange = viewModel::onEmailChange,
        modifier = Modifier
            .fillMaxWidth(),
        isError = emailError.value
    )

    val passwordTransformation = if (revealPassword.value)
        VisualTransformation.None
    else
        passwordTransformationInstance

    OutlinedTextField(
        value = passwordString.value,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                if (isSignUp.value) focusManager.moveFocus(FocusDirection.Down) else {
                    viewModel.onAuth()
                    focusManager.clearFocus()
                }
            }
        ),
        label = { Text(stringResource(R.string.password)) },
        shape = MaterialTheme.shapes.small,
        onValueChange = viewModel::onPasswordChange,
        visualTransformation = passwordTransformation,
        modifier = Modifier
            .fillMaxWidth(),
        trailingIcon = {
            IconToggleButton(
                checked = revealPassword.value,
                onCheckedChange = viewModel::onRevealPasswordsChange
            ) {
                Icon(
                    imageVector = if (revealPassword.value)
                        Icons.Filled.Visibility
                    else
                        Icons.Filled.VisibilityOff,
                    contentDescription = "Show password"
                )
            }
        },
        isError = passwordError.value
    )

    if (isSignUp.value)
        OutlinedTextField(
            value = repeatPasswordString.value,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { if(isSignUp.value) viewModel.onAuth() else focusManager.clearFocus() }
            ),
            label = { Text(stringResource(R.string.password)) },
            shape = MaterialTheme.shapes.small,
            onValueChange = viewModel::onRepeatPasswordChange,
            visualTransformation = passwordTransformation,
            modifier = Modifier
                .fillMaxWidth(),
            trailingIcon = {
                IconToggleButton(
                    checked = revealPassword.value,
                    onCheckedChange = viewModel::onRevealPasswordsChange
                ) {
                    Icon(
                        imageVector = if (revealPassword.value)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff,
                        contentDescription = "Show password"
                    )
                }
            },
            isError = repeatPasswordError.value
        )
}
