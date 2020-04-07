package com.example.kangur.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kangur.firebase.FirebaseAuthManager
import com.example.kangur.firebase.FirebaseDatabaseManager
import com.example.kangur.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignOutSignInViewModel:ViewModel() {

    private val firebaseAuthManager = FirebaseAuthManager()
    private val firebaseDataBaseManager= FirebaseDatabaseManager()
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
    fun getCurrentUser(setUser:(user: User) ->Unit){
        val currentUserUID = firebaseAuthManager.getUID()
        firebaseDataBaseManager.getCurrentUserInfo(currentUserUID, setUser)
    }
}