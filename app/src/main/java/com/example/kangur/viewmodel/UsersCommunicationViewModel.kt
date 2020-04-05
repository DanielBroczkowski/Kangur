package com.example.kangur.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kangur.firebase.FirebaseAuthManager
import com.example.kangur.firebase.FirebaseDatabaseManager
import com.example.kangur.model.ChatMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersCommunicationViewModel:ViewModel() {

    val firebaseDatabaseManager= FirebaseDatabaseManager()
    val firebaseAuthManager = FirebaseAuthManager()
    var messageList:MutableLiveData<ChatMessage> = MutableLiveData()

    fun sendMessage(toId : String , text:String)= CoroutineScope(Dispatchers.IO).launch {
        val chatMessage = ChatMessage(firebaseAuthManager.getUID(), null,text, System.currentTimeMillis() / 1000, toId)
        firebaseDatabaseManager.sendMessage(chatMessage)
    }
    fun listenForMessages() = CoroutineScope(Dispatchers.IO).launch {
        firebaseDatabaseManager.listenforMessages(firebaseAuthManager.getUID(), ::setMessages)
    }

    private fun setMessages(chatMessage: ChatMessage){
        messageList.postValue(chatMessage)
    }
}