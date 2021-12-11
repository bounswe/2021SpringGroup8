package com.example.facespaceextenstion

class Data {

    companion object {
        private var url = "http://3.145.120.66:8080/"
        private var username = ""
        private var email = ""
        private var name = ""
        private var surname = ""
        private var birthdate = ""
        private var city = ""
        private var pplink = ""
        private var token = ""
        private var currCommunityId = ""
        private var currCommunityTitle = ""
        private var currCommunityDesc = ""
        private var currCommunityDate = ""
        private var currCommunityCreator = ""
    }
    fun getUrl(extension:String): String {
        return url+extension
    }

    fun setAll(newUsername: String, newEmail:String, newName:String,
               newSurname:String, newBirth:String, newCity:String, newPPLink:String ) {
        username = newUsername
        email = newEmail
        name = newName
        surname = newSurname
        birthdate = newBirth
        city = newCity
        pplink = newPPLink

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
        setAll("","","","","","","")
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
    fun setCurrentComunityId(id: String) {
        currCommunityId = id
    }
    fun getCurrentComunityId(): String {
        return currCommunityId
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
}