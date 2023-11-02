package dev.catsuperberg.e_commerce_exercise.client.domain.service

interface IAccountService : IAuthState {
    suspend fun signIn(email: String, password: String): Result<String>
    suspend fun signUp(email: String, password: String): Result<String>
    fun signOut()
}
