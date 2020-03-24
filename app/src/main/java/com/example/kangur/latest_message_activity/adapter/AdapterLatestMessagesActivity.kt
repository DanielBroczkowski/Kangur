package com.example.kangur.latest_message_activity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kangur.R
import com.example.kangur.latest_message_activity.adapter.AdapterLatestMessagesActivity.*

class AdapterLatestMessagesActivity:RecyclerView.Adapter<ViewHolder>() {

    private var listOfItems = ArrayList<String>()

    fun refreshList(list: ArrayList<String>){
        listOfItems=list
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        //    val deleteButton= view.deleteButton!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.template_user_latest_message, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }
}