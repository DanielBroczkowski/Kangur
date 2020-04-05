package com.example.kangur.model

import java.sql.Timestamp

class ChatMessage(val fromId: String, var id: String?, val text:String, val timestamp:Long, val toId:String) {
    constructor() : this ("", "", "",-1,"")

}