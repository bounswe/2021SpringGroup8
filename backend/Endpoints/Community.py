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
    
    dbresult = manager.DatabaseManager.create_community(
        {
            "communityTitle": params["communityTitle"][0],
            "description": params["communityTitle"][0],
            "creationTime": datetime.now(),
        },
        {
            "id": userid,
            "username": "todo",
        }
    )

    return response