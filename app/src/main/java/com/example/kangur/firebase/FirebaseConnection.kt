package com.example.kangur.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class FirebaseConnection {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                "SUKCES NA ${it.result?.user?.uid}"
            }
    }
    fun login(email:String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(!it.isSuccessful)return@addOnCompleteListener

                "SUKCES NA ${it.result?.user?.uid}"
            }
    }
}