package com.example.kangur.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.kangur.firebase.FirebaseAuthManager
import com.example.kangur.firebase.FirebaseDatabaseManager
import com.example.kangur.model.ChatMessage
import com.example.kangur.model.LatestMessage
import com.example.kangur.model.User
import kotlinx.coroutines.*
import kotlin.system.exitProcess

class UsersCommunicationViewModel:ViewModel() {

    val firebaseDatabaseManager= FirebaseDatabaseManager()
    val firebaseAuthManager = FirebaseAuthManager()
    var messageList:MutableLiveData<ChatMessage> = MutableLiveData()
    var latestMessageList:MutableLiveData<HashMap<String, ChatMessage>> = MutableLiveData()
    var latestMessageList11:MutableLiveData<HashMap<String, LatestMessage>> = MutableLiveData()

    fun sendMessage(toId : String , text:String)= CoroutineScope(Dispatchers.IO).launch {
        val chatMessage = ChatMessage(firebaseAuthManager.getUID(), null,text, System.currentTimeMillis() / 1000, toId)
        firebaseDatabaseManager.sendMessage(chatMessage)
    }
    fun listenForMessages(interlocutorUID:String) = CoroutineScope(Dispatchers.IO).launch {
        firebaseDatabaseManager.listenforMessages(firebaseAuthManager.getUID(), interlocutorUID, ::setMessages)
    }
    fun listenForLatestMessages() = CoroutineScope(Dispatchers.IO).launch {
        val setLatestMessage:(HashMap<String,ChatMessage>?) -> Unit = { chatmessage ->
            chatmessage?.forEach {
                Log.d("asgasg", it.key)
                getInterlocutorData(it.key, it.value)
            }
        }
        firebaseDatabaseManager.listenForNewMessage(firebaseAuthManager.getUID(), setLatestMessage)
    }

    private fun setMessages(chatMessage: ChatMessage){
        messageList.value=(chatMessage)
    }
    //    private fun setLatestMessage(latestMessage: HashMap<String,ChatMessage>?){
//        latestMessageList.value=latestMessage
//    }
    fun getInterlocutorData(userUID:String, chatMessage: ChatMessage) {

        val userUIDset: (User?) -> Unit = {
            val hashMap= HashMap<String,LatestMessage>()
            hashMap[userUID]= LatestMessage(chatMessage, it)
            latestMessageList11.value = hashMap
        }
        firebaseDatabaseManager.getInhercostamData(userUID, userUIDset)
    }
}