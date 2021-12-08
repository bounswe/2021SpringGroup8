package com.example.facespace

class Data {

    companion object {
        private var username = ""
        private var token = ""
    }

    fun setUsername(newUsername:String) {
        username = newUsername
    }
    fun getUsername(): String {
        return username
    }
    fun setToken(newToken:String) {
        token = newToken
    }
    fun getToken(): String {
        return token
    }
    fun resetFields() {
        username = ""
        token = ""
    }
}