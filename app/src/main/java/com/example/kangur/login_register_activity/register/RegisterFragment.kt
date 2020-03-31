package com.example.kangur.login_register_activity.register

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.contentValuesOf
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.example.kangur.R
import com.example.kangur.firebase.FirebaseConnection
import com.example.kangur.login_register_activity.login.LoginFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import org.w3c.dom.Text
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {


    var selectedPhoto: Uri?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onStart() {
        super.onStart()
        switch_to_login_button.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
//        val passwordET=activity!!.findViewById<TextInputEditText>(R.id.new_password_edit_text)
//        val newloginET=activity!!.findViewById<TextInputEditText>(R.id.new_login_edit_text)
//        passwordET.addTextChangedListener(generalTextWatcher)
//        newloginET.addTextChangedListener(generalTextWatcher)

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
                Toast.makeText(requireActivity(),"Niepoprawne Dane", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            uploadImageToFireBaseStorage()
                FirebaseConnection().register(new_email_edit_text.text.toString(),new_password_edit_text.text.toString()) //MVVM!!!!!
            TODO() //MVVM
        }
        select_photo_button_register.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent, 0)
        }
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

    private fun uploadImageToFireBaseStorage(){ //uploads selected image to firebase storage. doesn't upload default image || atm here, need to put it into MVVM
        if(selectedPhoto==null) return

        val fileName=UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$fileName")
        ref.putFile(selectedPhoto!!).addOnSuccessListener {
            Toast.makeText(activity!!,"dodane", Toast.LENGTH_SHORT).show()
        }

        safeUserToFireBaseDataBase()
    }
    private fun safeUserToFireBaseDataBase(){
        val uid= FirebaseAuth.getInstance().uid
        FirebaseDatabase.getInstance().getReference("/users/$uid")
    }

}
