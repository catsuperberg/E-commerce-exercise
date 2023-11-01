package dev.catsuperberg.e_commerce_exercise.client.domain.service

import android.util.Log
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AccountService() : IAccountService {
    private val scope = CoroutineScope(Job() + Dispatchers.IO)
    private val auth = Firebase.auth
    private val currentUser = callbackFlow {
        val listener = AuthStateListener { auth ->
            this.trySend(auth.currentUser)
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }
    override val signedIn = currentUser
        .map { it != null }
        .stateIn(scope, SharingStarted.Eagerly, auth.currentUser != null)

    override suspend fun signIn(email: String, password: String): Result<String> = suspendCoroutine { continuation ->
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("D", "signInWithEmail:success")
                    auth.currentUser?.also {
                        continuation.resume(Result.success(it.uid))
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
