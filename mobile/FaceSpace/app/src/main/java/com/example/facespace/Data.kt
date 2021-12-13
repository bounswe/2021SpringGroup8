package com.example.facespaceextenstion

import com.example.facespace.Location
import org.json.JSONArray

import com.example.facespace.Location

class Data {

    companion object {
        private var url = "http://3.145.120.66:8080/"
        private var username = ""
        private var email = ""
        private var name = ""
        private var surname = ""
        private var birthdate = ""
        private var city = ""
        private var longitude = 0.0
        private var latitude = 0.0
        private var pplink = ""
        private var token = ""
        private var currCommunityId = ""
        private var currCommunityTitle = ""
        private var currCommunityDesc = ""
        private var currCommunityDate = ""
        private var currCommunityCreator = ""
<<<<<<< HEAD
        private var currPostId = ""
        private var currPostTitle = ""
        private var currPostDesc = ""
        private var currPostDate = ""
        private var currPostCreator = ""
        private lateinit var dataTypes: JSONArray
=======

>>>>>>> 98e7128ea7b8e9fbb696d31b8e80e09e8a78e2f3
    }
    fun getUrl(extension:String): String {
        return url+extension
    }

    fun setAll(newUsername: String, newEmail:String, newName:String,
               newSurname:String, newBirth:String, newCity:String, newPPLink:String,
<<<<<<< HEAD
               newLong: Double, newLat: Double) {
=======
                newLong: Double, newLat: Double) {
>>>>>>> 98e7128ea7b8e9fbb696d31b8e80e09e8a78e2f3
        username = newUsername
        email = newEmail
        name = newName
        surname = newSurname
        birthdate = newBirth
        city = newCity
        pplink = newPPLink
        longitude = newLong
        latitude = newLat

    }

    fun setName(newName: String) {
        name = newName
    }

    fun getAll(): MutableMap<String, String> {
        val infos: MutableMap<String, String> = HashMap()

        infos["username"] = username
        infos["email"] = email
        infos["name"] = name
        infos["surname"] = surname
        infos["birthdate"] = birthdate
        infos["city"] = city
        infos["pplink"] = pplink


        return infos
    }

    fun resetAll() {
        setAll("","","","","",
            "","", 0.0, 0.0)
    }

    fun getName(): String {
        return name
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
    fun setCurrentCommunityId(id: String) {
        currCommunityId = id
    }
    fun getCurrentCommunityId(): String {
        return currCommunityId
    }
    fun setCurrentPostId(id: String) {
        currPostId = id
    }
    fun getCurrentPostId(): String {
        return currPostId
    }
    fun resetFields() {
        username = ""
        token = ""
        name = ""
    }

    fun setCommInfo(title:String, desc:String, by:String, date:String) {
        currCommunityCreator = by
        currCommunityDate = date
        currCommunityDesc = desc
        currCommunityTitle = title
    }

    fun getCommInfo(): MutableMap<String, String> {
        val infos: MutableMap<String, String> = HashMap()

        infos["title"] = currCommunityTitle
        infos["desc"] = currCommunityDesc
        infos["by"] = currCommunityCreator
        infos["date"] = currCommunityDate

        return infos
    }

<<<<<<< HEAD
    fun setPostInfo(title:String, desc:String, by:String, date:String) {
        currPostCreator = by
        currPostDesc = desc
        currPostDate = date
        currPostTitle = title
    }

    fun getPostInfo(): MutableMap<String, String> {
        val infos: MutableMap<String, String> = HashMap()

        infos["title"] = currPostTitle
        infos["desc"] = currPostDesc
        infos["by"] = currPostCreator
        infos["date"] = currPostDate

        return infos
    }

    fun getLoc(): Location {
        return Location(city, longitude, latitude)
    }

=======
    fun getLoc(): Location {
        return Location(city, longitude, latitude)
    }
>>>>>>> 98e7128ea7b8e9fbb696d31b8e80e09e8a78e2f3
}