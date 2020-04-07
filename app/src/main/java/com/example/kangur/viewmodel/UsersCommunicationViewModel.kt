package com.example.kangur.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kangur.firebase.FirebaseAuthManager
import com.example.kangur.firebase.FirebaseDatabaseManager
import com.example.kangur.model.ChatMessage
import com.example.kangur.model.LatestMessage
import com.example.kangur.view.latest_message_activity.LatestMessagesActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersCommunicationViewModel:ViewModel() {

    val firebaseDatabaseManager= FirebaseDatabaseManager()
    val firebaseAuthManager = FirebaseAuthManager()
    var messageList:MutableLiveData<ChatMessage> = MutableLiveData()
    var latestMessageList:MutableLiveData<HashMap<String?,ChatMessage>> = MutableLiveData()

    fun sendMessage(toId : String , text:String)= CoroutineScope(Dispatchers.IO).launch {
        val chatMessage = ChatMessage(firebaseAuthManager.getUID(), null,text, System.currentTimeMillis() / 1000, toId)
        firebaseDatabaseManager.sendMessage(chatMessage)
    }
    fun listenForMessages(interlocutorUID:String) = CoroutineScope(Dispatchers.IO).launch {
        firebaseDatabaseManager.listenforMessages(firebaseAuthManager.getUID(), interlocutorUID, ::setMessages)
    }
    fun listenForLatestMessages() = CoroutineScope(Dispatchers.IO).launch {
        firebaseDatabaseManager.listenForNewMessage(firebaseAuthManager.getUID(), ::setLatestMessage)
    }

    private fun setMessages(chatMessage: ChatMessage){
        messageList.value=(chatMessage)
    }
    private fun setLatestMessage(latestMessage: HashMap<String?,ChatMessage>?){
        latestMessageList.value=latestMessage
    }
}