package com.example.signin_signup

data class Community
    (
    val title: String,
    val by: String,
    val desc: String,
    var isJoined: Boolean = false,
    var since:String
    )
