package com.example.kangur.firebase

import android.util.Log
import com.example.kangur.model.User
import com.google.firebase.database.FirebaseDatabase

class FirebaseDatabaseManager {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

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
}