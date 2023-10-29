package dev.catsuperberg.e_commerce_exercise.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.request.CachePolicy
import dev.catsuperberg.e_commerce_exercise.client.ui.FirebaseTestScreen
import dev.catsuperberg.e_commerce_exercise.client.ui.theme.EcommerceExerciseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageLoader = ImageLoader.Builder(this)
            .diskCache {
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.5)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .respectCacheHeaders(false)
            .build()


        Coil.setImageLoader(imageLoader)

        setContent {
            EcommerceExerciseTheme {
                FirebaseTestScreen(viewModel = viewModel())
            }
        }
    }
}
