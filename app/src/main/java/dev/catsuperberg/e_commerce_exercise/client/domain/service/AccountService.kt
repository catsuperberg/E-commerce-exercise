package dev.catsuperberg.e_commerce_exercise.client.domain.service

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AccountService(private val context: Context) : IAccountService {
    private val scope = CoroutineScope(Job() + Dispatchers.Default)
    private val auth = Firebase.auth
    private val currentUser = callbackFlow {
        val listener = AuthStateListener { auth ->
            val user = auth.currentUser
            this.trySend(auth.currentUser)
            signedIn.value = user != null
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }
    override val signedIn: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override suspend fun signIn(email: String, password: String): Result<String> = suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("D", "signInWithEmail:success")
                        auth.currentUser?.also {
                            continuation.resume(Result.success(it.uid))
                            signedIn.value = true
                        } ?: continuation.resume(Result.failure(Exception("No current user authenticated")))
                    } else {
                        Log.w("D", "signInWithEmail:failure", task.exception)
                        task.exception?.also {
                            continuation.resume(Result.failure(it))
                        }
                    }
                }
        }

    override suspend fun signUp(email: String, password: String): Result<String> = suspendCoroutine { continuation ->
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("D", "createUserWithEmail:success")
                    auth.currentUser?.also {
                        continuation.resume(Result.success(it.uid))
//                        signedIn.value = true
                    } ?: continuation.resume(Result.failure(Exception("No current user authenticated")))
                } else {
                    Log.w("D", "createUserWithEmail:failure", task.exception)
                    task.exception?.also {
                        continuation.resume(Result.failure(it))
                    }
                }
            }
    }

    override fun signOut() {
        auth.signOut()
    }
}
