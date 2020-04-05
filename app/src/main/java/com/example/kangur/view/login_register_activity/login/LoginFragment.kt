package com.example.kangur.view.login_register_activity.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.example.kangur.R
import com.example.kangur.view.latest_message_activity.LatestMessagesActivity
import com.example.kangur.viewmodel.SignOutSignInViewModel
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private lateinit var loginViewModel :SignOutSignInViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onStart() {
        super.onStart()
        loginViewModel = ViewModelProviders.of(this).get(SignOutSignInViewModel::class.java)

        switch_to_register_button.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        login_button.setOnClickListener {
            val login = login_tv_login_fragment.text.toString()
            val password =register_tv_login_fragment1.text.toString()
            if(login.isEmpty() || password.isEmpty())return@setOnClickListener

            loginViewModel.signIn(login,password)
        }

        loginViewModel.managedToLogIn.observe(this, Observer {
            if(it){
                toastMessage("Udało się poprawnie zalogować")
                val intent = Intent(context, LatestMessagesActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            else{
                toastMessage("Wystąpił błąd przy logowaniu")
            }
        })
    }

    private fun toastMessage(message:String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()

    }
}
