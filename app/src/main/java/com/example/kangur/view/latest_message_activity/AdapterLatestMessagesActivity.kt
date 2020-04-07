package com.example.kangur.view.latest_message_activity

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kangur.R
import com.example.kangur.model.ChatMessage
import com.example.kangur.model.LatestMessage
import com.example.kangur.model.User
import com.example.kangur.view.latest_message_activity.AdapterLatestMessagesActivity.*
import kotlinx.android.synthetic.main.template_user_latest_message.view.*

class AdapterLatestMessagesActivity(val context: Context, private val toMessageActivity:(user:User) -> Unit):RecyclerView.Adapter<ViewHolder>() {

    private var listOfItems = ArrayList<LatestMessage>()


    fun addItem(list: HashMap<String, LatestMessage>){
        list.values.forEach{
            listOfItems.add(it)

            listOfItems.sortByDescending { it.chatMessage.timestamp }
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
            val userName= view.textViewUserNameLatestMessages!!
            val userAvatar= view.imageViewLatestMessage!!
            val userMessage= view.textViewMessageLatestMessages!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.template_user_latest_message, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val act = listOfItems[position]
        Glide.with(context).load(Uri.parse(act.user!!.profileImage)).into(holder.userAvatar)
        holder.userMessage.text=act.chatMessage.text
        holder.userName.text= act.user!!.username

        holder.itemView.setOnClickListener {
            toMessageActivity(act.user!!)
        }

    }
}