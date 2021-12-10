package com.example.facespace

data class Community
    (
    val title: String,
    val by: String,
    val desc: String,
    var isJoined: Boolean = false,
    var since:String,
    var id: String
    )
