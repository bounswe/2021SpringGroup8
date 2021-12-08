package com.example.facespace

class Data {

    companion object {
        private var username = ""
        private var token = ""
        private var currCommunityId = ""
        private var currCommunityTitle = ""
        private var currCommunityDesc = ""
        private var currCommunityDate = ""
        private var currCommunityCreator = ""
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