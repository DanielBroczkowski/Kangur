package com.example.kangur.firebase

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class FirebaseConnection {

    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                "SUKCES NA ${it.result?.user?.uid}"
            }
    }
    suspend fun login(email:String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(!it.isSuccessful)return@addOnCompleteListener

                "SUKCES NA ${it.result?.user?.uid}"
            }
    }

    suspend fun uploadImageToFireBaseStorage(selectedPhoto:Uri) {

        val fileName = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$fileName")
        ref.putFile(selectedPhoto).addOnSuccessListener {
            Log.d("RegisterFragment", "Successfully uploaded image ${it.metadata?.path}")

            ref.downloadUrl.addOnSuccessListener {
                Log.d("RegisterFragment", "Successfully uploaded image $it")
                safeUserToFireBaseDataBase(it.toString(), "asgasga")
            }
        }
            .addOnFailureListener{

            }
    }
    fun safeUserToFireBaseDataBase(profileImgUrl: String, login:String){
        val uid= FirebaseAuth.getInstance().uid ?:""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, login, profileImgUrl)

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterFragment", "Successfully saved user to firebase database")
            }
    }
}