package com.example.kangur.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kangur.firebase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {
    private val firebaseAuthManager= FirebaseAuthManager()
    private val firebaseDatabaseManager = FirebaseDatabaseManager()
    private val firebaseStorageManager = FirebaseStorageManager()
    var isRegistered:MutableLiveData<Boolean> = MutableLiveData()



    fun createUserByEmail(selectedPhoto: Uri?, email: String, password: String, login:String) =
        CoroutineScope(Dispatchers.IO).launch {
            //ogolnie to to jest popierdolone XD jebałem się z tym trochę. Nigdy nie róbcie 3 rzeczy na raz w firebase bo to nie ma sensu
            // powinnismy najpierw zarejestrowac przez email -> nowy fragment z wyborem loginu -> i nowy fragment z dodaniem zdjecia
            // ale juz to jest zrobione wiec jebac. chyba ze komus sie chce zeby to ladnie wygladalo to prosze XD
            // ten val pierwszy userUIDset nic nie robi (na razie) wiec zaczynamy od linii 45
            val userUIDset :(String?)-> Unit= { uid->
                if(uid!=null) { // jestli nie jest nullem tzn ze udalo nam sie zarejstrowac wiec omijamy vala avatarImgUrl i...
                    val avatarImgUrl: (String?) -> Unit = { url -> //dodajemy usera do bazy danych z danymi ktore otrzymalismy
                        firebaseDatabaseManager.safeUserToFireBaseDataBase(url, uid, login)
                    }

                    if (selectedPhoto != null) { //jesli wybralismy zdjecie to uploadujemy je i przesylamy vala avatarImgUrl ktory nam zwroci url do naszego zdjecia
                        firebaseStorageManager.uploadUserAvatarImageToFireBaseStorage(selectedPhoto, avatarImgUrl)
                    }
                    else {
                        avatarImgUrl(selectedPhoto) //jak nie wybralismy zdjecia (czyli null) to omijamy storage i od razu dodajemy usera do bazy danych
                    }
                    isRegistered.postValue(true) //ogolnie udalo sie zarejestrowac (uuid nie jest nullem wiec dajemy do obserwowanego true)
                }
                else{
                    isRegistered.postValue(false) //ogolnie nie udalo sie zarejestrowac (uuid nie jest nullem wiec dajemy do obserwowanego false)
                }
            }

            firebaseAuthManager.register(email, password,userUIDset) //probujemy sie zarejestrowac. jak sie uda dostajemy uuid konta jak nie to dostaniemy null.
            //musimy cos dostac bo inaczej nie wywolamy vala userUIDset
        }

}