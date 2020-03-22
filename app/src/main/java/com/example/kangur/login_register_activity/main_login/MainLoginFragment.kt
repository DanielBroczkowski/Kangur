package com.example.kangur.login_register_activity.main_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.example.kangur.R
import com.example.kangur.login_register_activity.login.LoginFragment
import com.example.kangur.login_register_activity.options.LoginOptionsFragment
import kotlinx.android.synthetic.main.fragment_login_options.*

/**
 * A simple [Fragment] subclass.
 */
class MainLoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_login, container, false)
    }

    override fun onStart() {
        super.onStart()
    }
}
