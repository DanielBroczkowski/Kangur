package com.example.kangur.view.new_message_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kangur.R

class NewMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_newMessgae)
        val adapter =
            AdapterNewMessage()
        recyclerView.adapter=adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
