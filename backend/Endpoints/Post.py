from http.server import BaseHTTPRequestHandler
from json import encoder
from typing import Tuple
import urllib

from pymongo import database
from ServerManager import ServerManager
import json
import string
import unicodedata
import html
import hashlib
import re
from datetime import date, datetime

def SetError(response, err):
    response["@success"] = "False"
    response["@error"] = err
    return response

def Submit(manager : ServerManager, userid, params):
    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "Post.Submit"


    communityId = params["communityId"][0]
    title = params["title"][0]
    description = params["description"][0]
    
    userpreview = manager.DatabaseManager.get_user_preview(userid)
    
    if userpreview == False:
        return SetError(response, "User doesn't exist!")
    
    communitypreview = manager.DatabaseManager.get_community_preview(communityId)

    if communitypreview == False:
        return SetError(response, "Community doesn't exist!")
    
    if manager.DatabaseManager.is_subscribed(userid, communityId) == False:
        return SetError(response, "Can't send post!")

    post_dic = \
    {
        "postTitle": title,
        "description": description,
        "creationTime": datetime.now(),
    }

    dbres = manager.DatabaseManager.create_post(post_dic, userpreview, communitypreview)

    if dbres == False:
        return SetError(response, "Couldn't submit post!")

    dbres["@type"] = "Post.Object"
    dbres["postedBy"]["@type"] = "User.Preview"
    dbres["postedAt"]["@type"] = "Community.Preview"
    dbres["postedAt"]["createdBy"]["@type"] = "User.Preview"
    response["success"] = "True"
    response["@return"] = dbres
    return response

def View(manager : ServerManager, params):
    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "Post.View"

    postid = params["postId"][0]

    dbres = manager.DatabaseManager.get_specific_post(postid)

    if dbres == False:
        return SetError(response, "Couldn't find the post!")

    dbres["@type"] = "Post.Object"

    response["success"] = "True"
    response["@return"] = dbres
    return response


def Delete(manager : ServerManager, userid, params):
    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "Post.Delete"

    postid = params["postId"][0]
    postobject = manager.DatabaseManager.get_specific_post(postid)

    if postobject == False:
        return SetError(response, "Couldn't find the post!")

    if postobject["postedBy"]["id"] != userid:
        return SetError(response, "No permission!")

    res = manager.DatabaseManager.delete_post(postid)
    
    if res == False:
        return SetError(response, "Couldn't delete post!")
    
    response["success"] = "True"
    return response









