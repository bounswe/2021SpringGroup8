package com.example.facespace

import org.json.JSONArray

data class Community(
    val title: String,
    val by: String,
    val desc: String,
    val subscribers: JSONArray?,
    val posts: JSONArray?,
    val dataTypes: JSONArray?,
    var since:String,
    var id: String
    )
