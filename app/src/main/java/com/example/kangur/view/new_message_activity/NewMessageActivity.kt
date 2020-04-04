package com.example.kangur.view.new_message_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kangur.R
import com.example.kangur.model.User
import com.example.kangur.view.message_activity.MessageActivity
import com.example.kangur.viewmodel.AllUsersViewModel

class NewMessageActivity : AppCompatActivity() {


    private lateinit var allUsersViewModel : AllUsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        allUsersViewModel = ViewModelProviders.of(this).get(AllUsersViewModel::class.java)

        allUsersViewModel.fetchUsers()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_newMessgae)
        val adapter = AdapterNewMessage(this, ::loadMessageActivity)
        recyclerView.adapter=adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        allUsersViewModel.listofUsers.observe(this, Observer { adapter.addList(ArrayList(it)) })

    }
    private fun loadMessageActivity(toUser: User){
        val intent = Intent(this, MessageActivity::class.java)
        startActivity(intent)
        finish()
    }
}
