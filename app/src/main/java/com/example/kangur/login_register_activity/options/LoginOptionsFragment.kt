package com.example.kangur.login_register_activity.options

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.example.kangur.R
import com.example.kangur.login_register_activity.login.LoginFragment
import kotlinx.android.synthetic.main.fragment_login_options.*

/**
 * A simple [Fragment] subclass.
 */
class LoginOptionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_options, container, false)
    }

    override fun onStart() {
        super.onStart()
        login_with_email_option_button.setOnClickListener{
      //      findNavController().popBackStack(R.id.action_registerFragment_to_loginFragment, true)
            findNavController().navigate(R.id.loginFragment)
        }
    }

}
