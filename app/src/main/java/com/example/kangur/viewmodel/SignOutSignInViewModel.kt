package com.example.kangur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kangur.firebase.FirebaseAuthManager
import kotlinx.coroutines.CoroutineScope

class SignOutSignInViewModel:ViewModel() {

    private val firebaseAuthManager = FirebaseAuthManager()

    fun signOut(){
        firebaseAuthManager.signOut()
    }
}