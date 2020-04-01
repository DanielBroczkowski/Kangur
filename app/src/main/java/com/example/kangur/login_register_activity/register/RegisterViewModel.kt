package com.example.kangur.login_register_activity.register

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.kangur.firebase.FirebaseConnection
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val firebaseConnection = FirebaseConnection()

    fun createUserByEmail(selectedPhoto: Uri?, email: String, password: String) =
        CoroutineScope(Dispatchers.IO).launch {
            firebaseConnection.uploadImageToFireBaseStorage(selectedPhoto!!)
            firebaseConnection.register(email, password)
        }
}