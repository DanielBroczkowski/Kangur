package com.example.kangur.view.login_register_activity.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.example.kangur.R
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onStart() {
        super.onStart()
        switch_to_register_button.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        login_button.setOnClickListener {
            val login = login_tv_login_fragment.text.toString()
            val password =register_tv_login_fragment1.text.toString()
            if(login.isEmpty() || password.isEmpty())return@setOnClickListener

            //FirebaseConnection().login(login,password)
        }
    }
}
