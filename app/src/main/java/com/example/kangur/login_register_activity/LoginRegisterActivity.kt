package com.example.kangur.login_register_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.kangur.R
import com.example.kangur.login_register_activity.login.LoginFragment
import com.example.kangur.login_register_activity.register.RegisterFragment
import kotlinx.android.synthetic.main.activity_login_register.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LoginRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login_register)

        supportFragmentManager.beginTransaction().replace(R.id.container_login_register_switcher, LoginFragment()).commit()
    }
}
