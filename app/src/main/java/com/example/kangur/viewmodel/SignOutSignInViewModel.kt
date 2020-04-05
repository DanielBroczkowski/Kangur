package com.example.kangur.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kangur.firebase.FirebaseAuthManager
import kotlinx.coroutines.CoroutineScope

class SignOutSignInViewModel:ViewModel() {

    private val firebaseAuthManager = FirebaseAuthManager()
    var managedToLogIn: MutableLiveData<Boolean> = MutableLiveData()

    fun signOut(){
        firebaseAuthManager.signOut()
    }

    fun signIn(email:String, password:String){
        firebaseAuthManager.login(email,password, ::setmanagedToLogIn)
    }

    fun setmanagedToLogIn(onComplete:Boolean){
        managedToLogIn.postValue(onComplete)
    }
}