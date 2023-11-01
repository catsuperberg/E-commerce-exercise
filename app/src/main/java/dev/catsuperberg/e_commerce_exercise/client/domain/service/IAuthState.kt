package dev.catsuperberg.e_commerce_exercise.client.domain.service

import kotlinx.coroutines.flow.StateFlow

interface IAuthState {
    val signedIn: StateFlow<Boolean>
}
