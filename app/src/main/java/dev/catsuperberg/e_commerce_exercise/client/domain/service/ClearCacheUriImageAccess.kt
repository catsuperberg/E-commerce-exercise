package dev.catsuperberg.e_commerce_exercise.client.domain.service

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class ClearCacheUriImageAccess(private val context: Context) : IUriImageAccess {
    private val providerAuthority = "dev.catsuperberg.e_commerce_exercise.client.fileprovider"

    override fun cacheImageAndGetUri(fileName: String, bytes: ByteArray): Uri {
        val cacheDir = File(context.cacheDir, "images")
        cacheDir.deleteRecursively()
        cacheDir.mkdirs()
        val file = File(cacheDir, fileName)
        val uri = FileProvider.getUriForFile(context, providerAuthority, file)
        val outputStream = FileOutputStream(file)
        outputStream.write(bytes)
        outputStream.close()
        return uri
    }
}
