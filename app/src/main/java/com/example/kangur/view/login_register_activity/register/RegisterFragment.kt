package com.example.kangur.view.login_register_activity.register

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.Observer
import com.example.kangur.R
import com.example.kangur.view.latest_message_activity.LatestMessagesActivity
import com.example.kangur.viewmodel.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {


    var selectedPhoto: Uri?=null
    var registerViewModel= RegistrationViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onStart() {
        super.onStart()
        registerViewModel = ViewModelProviders.of(this).get(RegistrationViewModel::class.java)

        switch_to_login_button.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
//        val passwordET=activity!!.findViewById<TextInputEditText>(R.id.new_password_edit_text)
//        val newloginET=activity!!.findViewById<TextInputEditText>(R.id.new_login_edit_text)
//        passwordET.addTextChangedListener(generalTextWatcher)
//        newloginET.addTextChangedListener(generalTextWatcher)

        select_photo_button_register.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent, 0)
        }
        new_login_edit_text.setOnFocusChangeListener { v, hasFocus ->
            val login = new_login_edit_text.text.toString()
            if(hasFocus){
                new_login_text_input.isErrorEnabled=false
            }
            else{
                if(login.length<6)
                new_login_text_input.error="Za krótki login. Login musi mieć minimum 6 znaków"
            }
        }
        new_email_edit_text.setOnFocusChangeListener { v, hasFocus ->
        val email = new_email_edit_text.text.toString()
            if (hasFocus) {
                new_email_text_input.isErrorEnabled = false
            } else {
                if (!isEmailValid(email))
                    new_email_text_input.error = "Niepoprawny e-mail"
            }

        }
        new_password_edit_text.setOnFocusChangeListener { v, hasFocus ->
            val password =new_password_edit_text.text.toString()
            val passwordRep = new_password_repeat_edit_text.text.toString()
            if (hasFocus) {
                new_password_text_input.isErrorEnabled = false
                new_password_repeat_text_input.isErrorEnabled = false
            }
            else {
                if (password.length < 8) {
                    new_password_text_input.error = "Za krótkie hasło. Hasło musi mieć minimum 8 znaków"
                }
                if(passwordRep != password && !passwordRep.isEmpty()){
                    new_password_repeat_text_input.error = "Hasła nie są identyczne!"
                }
                else{
                    new_password_repeat_text_input.isErrorEnabled = false
                }
            }

        }
        new_password_repeat_edit_text.setOnFocusChangeListener { v, hasFocus ->
            val passwordRep = new_password_repeat_edit_text.text.toString()
            val password = new_password_edit_text.text.toString()
            if (hasFocus) {
                new_password_repeat_text_input.isErrorEnabled = false
            } else {
                if (passwordRep != password)
                    new_password_repeat_text_input.error = "Hasła nie są identyczne!"
            }

        }

        register_button.setOnClickListener {
            val login = new_login_edit_text.text.toString()
            val email = new_email_edit_text.text.toString()
            val password = new_password_edit_text.text.toString()
            val passwordRep= new_password_repeat_edit_text.text.toString()

            if (!isEmailValid(email) ||
                login.length<6 ||
                password.length<8 ||
                password!= passwordRep){
                toastMessage("Niepoprawne Dane")
                return@setOnClickListener
            }
            registerViewModel.createUserByEmail(selectedPhoto,email,password, login)
        }

        registerViewModel.isRegistered.observe(viewLifecycleOwner, Observer {
            if(it==true) {
                toastMessage("Udało się zarejestrować")
                val intent = Intent(activity, LatestMessagesActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            else{
                toastMessage("Wystąpił błąd przy rejestracji...")
            }

        })
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private var generalTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            when (s.hashCode()) {
                new_login_edit_text.text.hashCode() -> {
                }
                new_email_edit_text.text.hashCode() -> {
                }
                new_password_edit_text.text.hashCode() -> {
                    Log.d("holder", "holder")
                }
                new_password_repeat_edit_text.text.hashCode() -> {
                    Log.d("holder", "holder")
                }
//            }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==0 && resultCode== Activity.RESULT_OK &&data!=null){
            selectedPhoto = data.data
            val bitmap = ImageDecoder.createSource(activity!!.contentResolver, selectedPhoto!!)
            val bitmapDrawable= ImageDecoder.decodeDrawable(bitmap)
            select_photo_button_register.setImageDrawable(bitmapDrawable)
        }
    }

    private fun toastMessage(text:String){
        Toast.makeText(requireActivity(),text, Toast.LENGTH_SHORT).show()
    }
}
