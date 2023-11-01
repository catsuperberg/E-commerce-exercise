package dev.catsuperberg.e_commerce_exercise.client

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import dev.catsuperberg.e_commerce_exercise.client.domain.service.IAuthState
import dev.catsuperberg.e_commerce_exercise.client.presentation.node.MainNode
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.theme.EcommerceExerciseTheme
import org.koin.android.ext.android.inject

class MainActivity : NodeActivity() {
    private val authState: IAuthState by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcommerceExerciseTheme {
                Surface(tonalElevation = 0.2.dp) {
                    NodeHost(integrationPoint = appyxIntegrationPoint) {
                        MainNode(buildContext = it, authState)
                    }
                }
            }
        }
    }
}
