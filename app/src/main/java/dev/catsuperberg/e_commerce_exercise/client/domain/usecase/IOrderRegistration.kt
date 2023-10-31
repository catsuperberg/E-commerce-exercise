package dev.catsuperberg.e_commerce_exercise.client.domain.usecase

import dev.catsuperberg.e_commerce_exercise.client.domain.model.CustomerContactDetails
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item

interface IOrderRegistration {
    suspend fun open(item: Item, customer: CustomerContactDetails): Result<String>
}
