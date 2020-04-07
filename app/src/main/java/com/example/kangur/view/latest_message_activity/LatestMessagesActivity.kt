package com.example.kangur.view.latest_message_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kangur.R
import com.example.kangur.model.User
import com.example.kangur.view.login_register_activity.LoginRegisterActivity
import com.example.kangur.view.new_message_activity.NewMessageActivity
import com.example.kangur.viewmodel.SignOutSignInViewModel
import com.example.kangur.viewmodel.UsersCommunicationViewModel
import com.google.firebase.auth.FirebaseAuth

class LatestMessagesActivity : AppCompatActivity() {

    var signoutViewModel=SignOutSignInViewModel()
    var usersCommunicationViewModel= UsersCommunicationViewModel()

    companion object{
        var currentUser:User?= null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signoutViewModel.getCurrentUser(::setCurrentUser)

        signoutViewModel = ViewModelProviders.of(this).get(SignOutSignInViewModel()::class.java)
        usersCommunicationViewModel = ViewModelProviders.of(this).get(UsersCommunicationViewModel::class.java)

        usersCommunicationViewModel.listenForLatestMessages()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_latestmessages)
        val adapter =
            AdapterLatestMessagesActivity(this)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)
        usersCommunicationViewModel.latestMessageList11.observe(this, Observer { adapter.addItem(it) })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_new_message -> {
                val intent = Intent(this, NewMessageActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_sign_ouy -> {
                val intent = Intent(this, LoginRegisterActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                signoutViewModel.signOut()
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setCurrentUser(user:User){
        currentUser=user
    }

//    private fun getInterlocutorData(interUID:String): User{
//        Log.d("pizdaaaaaa", usersCommunicationViewModel.getInterlocutorData(interUID).username)
//        return usersCommunicationViewModel.getInterlocutorData(interUID)
//    }
}

