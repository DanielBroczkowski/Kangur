package com.example.kangur.model

import com.example.kangur.serializable.UserSerializable

class User(val uid:String, val username:String, val profileImage:String):UserSerializable {
    constructor() : this ("", "", "")
    override fun getid(): String {
        return uid
    }

    override fun getlogin(): String {
        return username
    }

    override fun getimage(): String {
        return  profileImage
    }
}