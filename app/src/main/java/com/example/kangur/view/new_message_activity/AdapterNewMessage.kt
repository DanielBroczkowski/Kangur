package com.example.kangur.view.new_message_activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kangur.R
import com.example.kangur.model.User
import com.example.kangur.view.new_message_activity.AdapterNewMessage.*
import com.example.kangur.viewmodel.AllUsersViewModel
import kotlinx.android.synthetic.main.template_user_new_message.view.*

class AdapterNewMessage(private var context: Context, loadMessageActivity:(toUser:User)-> Unit): RecyclerView.Adapter<ViewHolder>() {

    private var listOfUsers = ArrayList<User>()
    private var loadMessageActivity: ((User) -> Unit)?=null
    init {
        this.loadMessageActivity=loadMessageActivity
    }
    fun addList(list:ArrayList<User>){
        listOfUsers=list
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val image= view.imageViewNewMessage!!
        val nickname = view.newMessageUserName!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.template_user_new_message, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val act = listOfUsers[position]

        Glide.with(context)
            .load(act.profileImage)
            .into(holder.image)
        holder.nickname.text=act.username

        holder.itemView.setOnClickListener {
            loadMessageActivity?.let { it1 -> it1(act) }
        }
    }

}