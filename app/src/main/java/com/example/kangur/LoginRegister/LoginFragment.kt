package com.example.kangur.LoginRegister


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.kangur.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    var fragmentIsLogin = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        buttonSwapLogin.setOnClickListener{
            if(!fragmentIsLogin)
            swapFragments()
        }
        buttonSwapRegister.setOnClickListener{
            if(fragmentIsLogin)
            swapFragments()
        }
        buttonRegister.setOnClickListener{
            val email = editTextEmailRegister.text.toString()
            val login = editTextLoginRegister.text.toString()
            val password = editTextPasswordRegister.text.toString()
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{

                    if (!it.isSuccessful) return@addOnCompleteListener

                    //else if successful
                    Log.d("xddd", "UDALO SIE ${it.result?.user?.uid}")
                }
                .addOnFailureListener {
                    Log.d("loser", it.message)
                }
        }

        buttonLogin.setOnClickListener{
            val email = editTextLoginLogin.text.toString()
            val password = editTextPasswordLogin.text.toString()
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if (!it.isSuccessful) return@addOnCompleteListener

                    //else if successful
                    Log.d("xddd", "UDALO SIE ${it.result?.user?.uid}")
                }
                .addOnFailureListener{
                    Log.d("loser", it.message)
                }
        }
    }

    fun swapFragments(){
        if(fragmentIsLogin){
            fragmentIsLogin=false
            activity!!.findViewById<ConstraintLayout>(R.id.layoutLogin).visibility = View.GONE
            activity!!.findViewById<ConstraintLayout>(R.id.layoutRegister).visibility = View.VISIBLE
        }
        else{
            fragmentIsLogin=true
            activity!!.findViewById<ConstraintLayout>(R.id.layoutLogin).visibility = View.VISIBLE
            activity!!.findViewById<ConstraintLayout>(R.id.layoutRegister).visibility = View.GONE
        }
    }

}

