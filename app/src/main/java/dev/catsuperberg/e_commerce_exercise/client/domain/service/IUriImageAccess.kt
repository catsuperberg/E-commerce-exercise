package dev.catsuperberg.e_commerce_exercise.client.domain.service

import android.net.Uri

interface IUriImageAccess {
    fun cacheImageAndGetUri(fileName: String, bytes: ByteArray): Uri
}
