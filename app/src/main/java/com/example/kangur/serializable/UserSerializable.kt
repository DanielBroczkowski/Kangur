package com.example.kangur.serializable

import java.io.Serializable

interface UserSerializable:Serializable {

    fun getid():String
    fun getlogin():String
    fun getimage():String
}