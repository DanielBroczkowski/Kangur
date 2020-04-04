package com.example.kangur.view.splash_screen_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kangur.R
import com.example.kangur.view.latest_message_activity.LatestMessagesActivity
import com.example.kangur.view.login_register_activity.LoginRegisterActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        CoroutineScope(Dispatchers.IO).launch {
            funkcjaxD()
        }
    }
    suspend fun funkcjaxD(){
        delay(3000L)
        val uid= FirebaseAuth.getInstance().uid
        if(uid==null){
            val intent = Intent(this, LoginRegisterActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        else{
            val intent = Intent(this, LatestMessagesActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}
