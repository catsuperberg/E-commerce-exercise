package dev.catsuperberg.e_commerce_exercise.client.domain.exception

sealed class AppException {
    data class UninitializedException(override val message: String) : Exception(message)
    data class NoValueException(override val message: String) : Exception(message)
}
