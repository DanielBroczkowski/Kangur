package com.example.kangur.view.message_activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kangur.R
import com.example.kangur.firebase.FirebaseAuthManager
import com.example.kangur.model.User
import com.example.kangur.view.latest_message_activity.LatestMessagesActivity
import com.example.kangur.viewmodel.UsersCommunicationViewModel
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : AppCompatActivity() {

    private var usersCommunicationViewModel= UsersCommunicationViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val interlocutor = intent.getSerializableExtra("user") as User
        title=interlocutor.getlogin()
        setContentView(R.layout.activity_message)
        usersCommunicationViewModel = ViewModelProviders.of(this).get(UsersCommunicationViewModel::class.java)

        usersCommunicationViewModel.listenForMessages(interlocutor.uid)


        val adapter = MessageAdapter(this, interlocutor, LatestMessagesActivity.currentUser!!)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_message)
        recyclerView.adapter=adapter
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd=true
        recyclerView.layoutManager = linearLayoutManager
        usersCommunicationViewModel.messageList.observe(this, Observer { adapter.onNewMessageCame(it)
            recyclerView.smoothScrollToPosition(adapter.itemCount)
        })

        enterMessageImageButton.setOnClickListener{
            if(!test_to_send.text.isNullOrEmpty()) {
                Log.d("MessageActivity", "Attempt to send message...")
                val message = test_to_send.text.toString()
                usersCommunicationViewModel.sendMessage(interlocutor.uid, message)
                test_to_send.text = null
                recyclerView.smoothScrollToPosition(adapter.itemCount)
            }
        }

        //QR code scanner
        takePhotoImageButton.setOnClickListener{
            val scanner = IntentIntegrator(this)

            scanner.initiateScan()
        }
    }

    override fun onDestroy() {

        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if(result != null) {
                if(result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    test_to_send?.setText(result.contents.toString())
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
