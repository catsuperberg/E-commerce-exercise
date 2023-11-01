package dev.catsuperberg.e_commerce_exercise.client.domain.service

import kotlinx.coroutines.flow.StateFlow

interface IAccountService : IAuthState {
    override val signedIn: StateFlow<Boolean>

    suspend fun signIn(email: String, password: String): Result<String>
    suspend fun signUp(email: String, password: String): Result<String>
    fun signOut()
}
