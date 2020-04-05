package com.example.kangur.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class FirebaseAuthManager {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun register(email: String, password: String, onSuccessUserUid:(String?)-> Unit) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful)return@addOnCompleteListener

                Log.d("RegisterFragment", "Successfully added new user by email")
                onSuccessUserUid(it.result?.user?.uid) //wywołuje funkcje z viewmodelu w celu przekazania UID otrzymanego przy rejestracji
            }
            .addOnFailureListener {
                onSuccessUserUid(null) // podobnie z tym ze przekazuje nulla bo null ważny (więcej info w viewmodel czemu)
                Log.d("RegisterFragment", "Failed add new user by email")
            }

    }

    fun login(email:String, password: String, onCompleted:(b: Boolean) -> Unit){ //na razie nie ruszane
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(!it.isSuccessful)return@addOnCompleteListener
                onCompleted(true)
                "SUKCES NA ${it.result?.user?.uid}"
            }
            .addOnFailureListener{
                onCompleted(false)
            }
    }

    fun signOut(){
        firebaseAuth.signOut()
    }
    fun getUID() :String{
        return firebaseAuth.currentUser?.uid.toString()
    }
}