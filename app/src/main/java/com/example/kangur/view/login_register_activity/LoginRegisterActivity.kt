package com.example.kangur.view.login_register_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kangur.R

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LoginRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login_register)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}
