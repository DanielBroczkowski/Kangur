package com.example.kangur.view.new_message_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kangur.R
import com.example.kangur.view.new_message_activity.AdapterNewMessage.*

class AdapterNewMessage(): RecyclerView.Adapter<ViewHolder>() {

    private var listOfUsers = ArrayList<String>()

    fun refreshList(list:ArrayList<String>){
        listOfUsers = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
    //    val deleteButton= view.deleteButton!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.template_user_new_message, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

}