package dev.catsuperberg.e_commerce_exercise.client

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import dev.catsuperberg.e_commerce_exercise.client.domain.exception.AppException.NoValueException
import dev.catsuperberg.e_commerce_exercise.client.domain.service.IAuthState
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IMediaRequestHoisting
import dev.catsuperberg.e_commerce_exercise.client.presentation.node.MainNode
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.theme.HalloweenTheme
import org.koin.android.ext.android.inject

class MainActivity : NodeActivity() {
    private val authState: IAuthState by inject()
    private val mediaRequestHoisting: IMediaRequestHoisting by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hookUpImagePicker()

        setContent {
            HalloweenTheme {
                Surface(tonalElevation = 0.5.dp) {
                    NodeHost(integrationPoint = appyxIntegrationPoint) {
                        MainNode(buildContext = it, authState)
                    }
                }
            }
        }
    }

    private fun hookUpImagePicker() {
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null)
                mediaRequestHoisting.selectedImage(Result.success(uri))
            else
                mediaRequestHoisting.selectedImage(Result.failure(NoValueException("PickVisualMedia: No media selected")))
        }
        mediaRequestHoisting.registerRequester(pickMedia)
    }
}
