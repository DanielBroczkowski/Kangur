package com.example.kangur.login_register_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.kangur.R
import com.example.kangur.login_register_activity.login.LoginFragment
import com.example.kangur.login_register_activity.main_login.MainLoginFragment
import com.example.kangur.login_register_activity.options.LoginOptionsFragment
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
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
