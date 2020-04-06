package com.example.kangur.view.message_activity

import android.content.Context
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.media.Image
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kangur.R
import com.example.kangur.model.ChatMessage
import com.example.kangur.model.User
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.message_from_template.view.*
import kotlinx.android.synthetic.main.message_to_template.view.*
import kotlinx.android.synthetic.main.template_user_new_message.view.*

//loadMessageActivity:(toUser:User)-> Unit): RecyclerView.Adapter<ViewHolder>()
class MessageAdapter(private var context: Context, private val interlocutor: User, private val currentUser:User): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfMessages = ArrayList<ChatMessage>()
    private val TYPE_TO =1;
    private val TYPE_FROM =2;
    private val replaceImageWithGliseInterlocutor= Glide.with(context).load(Uri.parse(interlocutor.profileImage))
    private val replaceImageWithGliseThisUser= Glide.with(context).load(Uri.parse(currentUser.profileImage))

    fun onNewMessageCame(chatMessage: ChatMessage){
        listOfMessages.add(chatMessage)
        notifyDataSetChanged()
    }


    inner class ViewHolderTO(view: View): RecyclerView.ViewHolder(view) {
        val imageto = view.user_to_avatar!!
        val messageto = view.message_to_textView!!
    }
    inner class ViewHolderFROM(view: View): RecyclerView.ViewHolder(view) {
        val imagefrom = view.user_from_avatar!!
        val messagefrom = view.message_from_textView!!
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==TYPE_TO){
            val view = LayoutInflater
                .from(parent.context).inflate(R.layout.message_to_template, parent, false)
            ViewHolderTO(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.message_from_template, parent, false)
            ViewHolderFROM(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(listOfMessages[position].fromId==interlocutor.uid){
            return TYPE_TO
        }
        else {
            return TYPE_FROM
        }
    }

    override fun getItemCount(): Int {
        return listOfMessages.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val act = listOfMessages[position]
        Log.d("xdddd", act.fromId)
        if(getItemViewType(position)==TYPE_FROM){
            (holder as ViewHolderFROM).messagefrom.text=act.text
            replaceImageWithGliseThisUser.into((holder as ViewHolderFROM).imagefrom)
        }
        else if(getItemViewType(position)==TYPE_TO){
            (holder as ViewHolderTO).messageto.text=act.text
            replaceImageWithGliseInterlocutor.into((holder as ViewHolderTO).imageto)
        }
    }
}