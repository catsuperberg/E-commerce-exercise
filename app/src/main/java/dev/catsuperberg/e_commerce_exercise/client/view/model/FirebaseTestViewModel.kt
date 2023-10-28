package dev.catsuperberg.e_commerce_exercise.client.view.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow

class FirebaseTestViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    val items = MutableStateFlow<List<String>>(listOf())

    init {
        db.collection("items").get()
            .addOnSuccessListener { snapshot ->
                val names = snapshot.documents.mapNotNull { it.getString("name") }
                items.value = names
            }
            .addOnFailureListener{
                Log.d("ERROR", "Failed collection read")
            }
    }
}
