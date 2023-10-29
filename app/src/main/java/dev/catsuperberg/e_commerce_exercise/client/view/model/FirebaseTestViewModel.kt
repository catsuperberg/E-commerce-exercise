package dev.catsuperberg.e_commerce_exercise.client.view.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow


data class Item(
    val name: String?,
    val imagePath: String?
)

class FirebaseTestViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    val items = MutableStateFlow<List<Item>>(listOf())

    init {
        db.collection("items").get()
            .addOnSuccessListener { snapshot ->
                val names = snapshot.documents.mapNotNull {
                    Item(
                        it.getString("name"),
                        it.getString("picture")
                    )
                }
                items.value = names
            }
            .addOnFailureListener{
                Log.d("ERROR", "Failed collection read")
            }
    }
}
