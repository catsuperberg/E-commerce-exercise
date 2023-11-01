package dev.catsuperberg.e_commerce_exercise.client.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.auth.IAuthViewModel

@Composable
fun AuthScreen(viewModel: IAuthViewModel) {
    val loggedIn = viewModel.authenticated.collectAsState()
    
    val isSignUp = viewModel.isSignUp.collectAsState()
    
    val emailString = viewModel.email.collectAsState()
    val passwordString = viewModel.password.collectAsState()
    val repeatPasswordString = viewModel.repeatPassword.collectAsState()
    
    val revealPassword = viewModel.revealPasswords.collectAsState()

    val emailError = viewModel.emailInvalid.collectAsState()
    val passwordError = viewModel.passwordInvalid.collectAsState()
    val repeatPasswordError = viewModel.repeatPasswordInvalid.collectAsState()
    
    
    val focusManager = LocalFocusManager.current
    val passwordTransformationInstance = remember { PasswordVisualTransformation() }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
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

        if(isSignUp.value)
            OutlinedTextField(
                value = repeatPasswordString.value,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.clearFocus() }
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



        Text("Logged in: ${loggedIn.value}")
        Spacer(modifier = Modifier.height(16.dp))

        val authText = if(isSignUp.value) stringResource(R.string.sign_up) else stringResource(R.string.sign_in)
        Button(onClick = { viewModel.onAuth() }) {
            Text(authText)
        }
        Button(onClick = { viewModel.onSignOut() }) {
            Text("Sign out")
        }
        Row(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSignUp.value,
                onCheckedChange = viewModel::onSignUpChange
            )
            Text(stringResource(R.string.user_sign_up))
        }
    }
}
