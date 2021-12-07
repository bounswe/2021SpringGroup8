package com.example.facespace

class Data {

    companion object {
        private var username = "bos"
    }

    fun setUsername(newUsername:String) {
        username = newUsername
    }
    fun getUsername(): String {
        return username
    }
}