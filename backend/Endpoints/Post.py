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

