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
    
    userobject = manager.DatabaseManager.find_user(userid)
    
    if userobject == False:
        response["@success"] = "False"
        response["@error"] = "User is not found!"
        return response

    dbresult = manager.DatabaseManager.create_community(
        {
            "communityTitle": params["communityTitle"][0],
            "description": params["description"][0],
            "creationTime": datetime.now(),
        },
        {
            "id": userid,
            "username": userobject["username"],
        }
    )
    
    if dbresult == False:
        response["@success"] = "False"
        response["@error"] = "Couldn't create a community!"
        return response

    response["@success"] = "True"
    dbresult["@type"] = "Community.Object"
    dbresult["creationTime"] = dbresult["creationTime"].isoformat()
    response["@return"] = dbresult

    return response