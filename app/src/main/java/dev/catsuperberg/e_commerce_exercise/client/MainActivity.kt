package dev.catsuperberg.e_commerce_exercise.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.catsuperberg.e_commerce_exercise.client.ui.FirebaseTestScreen
import dev.catsuperberg.e_commerce_exercise.client.ui.theme.EcommerceExerciseTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcommerceExerciseTheme {
                FirebaseTestScreen(viewModel = viewModel())
            }
        }
    }
}
