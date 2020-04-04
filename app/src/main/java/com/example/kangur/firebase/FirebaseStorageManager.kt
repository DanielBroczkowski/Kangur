package com.example.kangur.firebase

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class FirebaseStorageManager {

    private val firebaseStorage = FirebaseStorage.getInstance()

    fun uploadUserAvatarImageToFireBaseStorage(selectedPhoto: Uri, onSuccess:(String?) -> Unit) {

        val fileName = UUID.randomUUID().toString() //tworzenie uuid nowego zdjecia
        val ref = firebaseStorage.getReference("/images/$fileName") // tworzenie pliku o nazwie filename w firebase storage
        ref.putFile(selectedPhoto).addOnSuccessListener {
            Log.d("RegisterFragment", "Successfully uploaded image ${it.metadata?.path}")

            ref.downloadUrl.addOnSuccessListener {
                Log.d("RegisterFragment", "Successfully uploaded image $it")
                onSuccess(it.toString()) //przesylanie linku url naszego zdjecia do viewmodel
            }
        }
            .addOnFailureListener {
                Log.d("RegisterFragment", "Failed to  uploade image $it")
                return@addOnFailureListener
            }
    }
}