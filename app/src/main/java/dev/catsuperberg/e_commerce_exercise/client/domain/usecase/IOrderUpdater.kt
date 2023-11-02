package dev.catsuperberg.e_commerce_exercise.client.domain.usecase

interface IOrderUpdater {
    fun fulfill(id: String)
    fun cancel(id: String)
}
