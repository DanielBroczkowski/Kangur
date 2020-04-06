package com.example.kangur.firebase

import android.util.Log
import com.example.kangur.model.ChatMessage
import com.example.kangur.model.User
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class FirebaseDatabaseManager {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private var list:ArrayList<User> = ArrayList()
    var currentUser:User? =null

    fun safeUserToFireBaseDataBase(profileImgUrl: String?, uid:String, login:String) {
        val pictureurl:String
        if(profileImgUrl.isNullOrEmpty()){
            pictureurl="https://firebasestorage.googleapis.com/v0/b/kangur-55197.appspot.com/o/images%2F69d0410c-f2db-41ad-b917-e27f1675f747?alt=media&token=2bc9e42b-f338-4bd3-af5e-90bf2b9b161e"
        } else{
            pictureurl=profileImgUrl
        } // linie 12-17: jezeli nie wybralismy zdjecia to ustawiamy zdjecie domyslne ktore jest juz w bazie (nie ma sensu przesylac wiele razy domyslnego zdjecia)

        val ref = firebaseDatabase.getReference("/users/$uid") // dodanie uid utworzonego konta do bazy danych
        val user = User(uid, login, pictureurl) //elementy ktore do podanego uid maja dolaczyć (wiecej w firebase -> database jak to wyglada)
        ref.setValue(user) //no i dodajemy te value
            .addOnSuccessListener {
                Log.d("RegisterFragment", "Successfully saved user to firebase database")
                return@addOnSuccessListener
            }
            .addOnFailureListener {
                Log.d("RegisterFragment", "Failed to save user to firebase database")
                return@addOnFailureListener
            }
    }
    fun fetchUsers(listofUsers:(c: ArrayList<User>) -> Unit?){
        val ref = firebaseDatabase.getReference("/users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val user = it.getValue(User::class.java)
                    list.add(user!!)
                }
                listofUsers(list)
                Log.d("NewMessageActivity", "Successfully fetched users from firebase database")
            }
            override fun onCancelled(p0: DatabaseError) {
                Log.d("NewMessageActivity", "Failed to fetch users from firebase database")
            }
        })
    }

    fun sendMessage(chatMessage:ChatMessage){
        val ref = firebaseDatabase.getReference("/messages").push()
        chatMessage.id= ref.key
        ref.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d("MessageActivity", "Saved message in FireBaseDataBase")
            }
            .addOnFailureListener{
                Log.d("MessageActivity", "Failed to save message in FireBaseDataBase")
            }
    }

    fun listenforMessages(myUid:String,interlocutorUID:String, setMessages:(chatmessage:ChatMessage)-> Unit){
        val ref = firebaseDatabase.getReference("/messages")

        ref.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)
                if(chatMessage!=null) {
                    Log.d("MessageActivity", "New message arrived")
                    if (chatMessage.fromId == myUid && chatMessage.toId==interlocutorUID) {
                        setMessages(chatMessage)
                    }
                    else if(chatMessage.toId==myUid && chatMessage.fromId==interlocutorUID){
                        setMessages(chatMessage)
                    }
                }
            }
            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })
    }
    fun getCurrentUserInfo(setUser:String, unit:(User) -> Unit){
        Log.d("test",setUser)
        val ref = firebaseDatabase.getReference("/users/$setUser")
        ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val currentUser = p0.getValue(User::class.java)
                unit(currentUser!!)
            }

        })
    }
}