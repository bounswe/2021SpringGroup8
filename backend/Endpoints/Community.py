from http.server import BaseHTTPRequestHandler
from json import encoder
from typing import Tuple
import urllib
from ServerManager import ServerManager
import json
import string
import unicodedata
import html
import hashlib
import re
from datetime import datetime

def CreateCommunity(manager : ServerManager, userid, params):
    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "Community.Create"
    
    userprevobject = manager.DatabaseManager.get_user_preview(userid)
    
    if userprevobject == False:
        response["@success"] = "False"
        response["@error"] = "User is not found!"
        return response

    dbresult = manager.DatabaseManager.create_community(
        {
            "communityTitle": params["communityTitle"][0],
            "description": params["description"][0],
            "creationTime": datetime.now(),
        },
        userprevobject
    )
    
    if dbresult == False:
        response["@success"] = "False"
        response["@error"] = "Couldn't create a community!"
        return response

    response["@success"] = "True"
    dbresult["@type"] = "Community.Object"
    dbresult["creationTime"] = dbresult["creationTime"]
    dbresult["createdBy"]["@type"] = "User.Preview"
    for sub in dbresult["subscribers"]:
        sub["@type"] = "User.Preview"

    response["@return"] = dbresult

    return response

def SubscribeToCommunity(manager : ServerManager, userid, params):
    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "Community.SubscribeTo"
    

    communityId = params["communityId"][0]
    communityPreview = manager.DatabaseManager.get_community_preview(communityId)

    if communityPreview == False:
        response["@success"] = "False"
        response["@error"] = "Couldn't find the community!"
        return response

    if manager.DatabaseManager.is_subscribed(userid, communityId) == True:
        return SetError(response, "Already subscribed!")

    dbresult = manager.DatabaseManager.subscribe_community(userid, communityPreview)
    
    if dbresult == False:
        response["@success"] = "False"
        response["@error"] = "Couldn't Subscribe!"
        return response
    
    response["@success"] = "True"
    return response

def GetCommunity(manager : ServerManager, params):
    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "Community.Get"
    
    communityId = params["communityId"][0]

    dbresult = manager.DatabaseManager.get_specific_community(communityId)

    if dbresult == False:
        response["@success"] = "False"
        response["@error"] = "Can't get the community!"
        return response

    response["@success"] = "True"
    dbresult["@type"] = "Community.Object"
    response["@return"] = dbresult

    return response

def GetAllCommunities(manager : ServerManager, params):
    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "Community.GetAll"
    
    dbresult = manager.DatabaseManager.get_communuties()

    if dbresult == False:
        response["@success"] = "False"
        response["@error"] = "Can't get communities!"
        return response

    response["@success"] = "True"

    for x in dbresult:
        x["@type"] = "Community.Preview"

    response["@return"] = dbresult

    return response


def DeleteCommunity(manager : ServerManager, userid, params):
    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "Community.Delete"
    
    communityId = params["communityId"][0]
    
    communityobject = manager.DatabaseManager.get_specific_community(communityId)

    if communityobject == False:
        response["@success"] = "False"
        response["@error"] = "Can't find the community!"
        return response

    if communityobject["createdBy"]["id"] != userid:
        response["@success"] = "False"
        response["@error"] = "Community can only be deleted by the user who created the community!"
        return response
    
    res = manager.DatabaseManager.delete_community(communityId)

    if res == False:
        response["@success"] = "False"
        response["@error"] = "Can't delete the community!"
        return response

    response["@success"] = "True"
    return response

    

def SetError(response, err):
    response["@success"] = "False"
    response["@error"] = err
    return response

def Unsubscribe(manager : ServerManager, userid, params):
    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "Community.Unsubscribe"
    
    communityId = params["communityId"][0]

    communitypreview = manager.DatabaseManager.get_community_preview(communityId)
    
    if communitypreview == False:
        return SetError(response, "Can't find the community!")
    
    dbres = manager.DatabaseManager.unsubscribe_community(userid, communitypreview)

    if dbres == False:
        return SetError(response, "Can't unsubscribe the community!")

    response["@success"] = "True"
    return response

