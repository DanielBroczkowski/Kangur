package com.example.kangur.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kangur.firebase.FirebaseAuthManager
import com.example.kangur.firebase.FirebaseDatabaseManager
import com.example.kangur.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllUsersViewModel: ViewModel() {

    private val firebaseDatabaseManager = FirebaseDatabaseManager()
    private val firebaseAuthManager = FirebaseAuthManager()
    var listofUsers: MutableLiveData<List<User>> = MutableLiveData()


    fun fetchUsers() = CoroutineScope(Dispatchers.IO).launch{
        firebaseDatabaseManager.fetchUsers(::setListOfUsers)
    }

    private fun setListOfUsers(list: ArrayList<User>){
        var removePositin : Int? = null
        val useruid= firebaseAuthManager.getUID()
        list.forEachIndexed { index, user ->
            Log.d("tester", user.uid + " " + useruid)
            if(user.uid== useruid){
                Log.d("tester", user.uid + " " + useruid)
                removePositin= index
            }
        }
        list.removeAt(removePositin!!)
        listofUsers.value=list
    }
}