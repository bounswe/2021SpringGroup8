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
from .ObjectChecker import CheckLocation
from .ObjectChecker import CheckEmail
from .ObjectChecker import CheckPassword

def SetError(response, err):
    response["@success"] = "False"
    response["@error"] = err
    return response

def Hash(password):
    return hashlib.sha256(password.encode('utf-8')).hexdigest()

def Login(manager : ServerManager, params):
    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "User.Login"

    username = params["username"][0]
    password = params["password"][0]
    hashedpassword = Hash(password)

    dbresult = manager.DatabaseManager.signin(
        {
            "username": username,
            "password": hashedpassword
        }
    )

    if dbresult == False:
        response["@success"] = "False"
        response["@error"] = "User not found or password don't match!"
        return response

    response["@success"] = "True"
    dbresult["@type"] = "User.Object"
    response["@return"] = dbresult
    response["@usertoken"] = manager.RegisterToken(dbresult)

    return response


def SignUp(manager : ServerManager, params):
    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "User.SignUp"

    username = params["username"][0]
    email = params["email"][0]
    password = params["password"][0]
    name = params["name"][0]
    surname = params["surname"][0]
    birthdate = datetime.fromisoformat(params["birthdate"][0]) if "birthdate" in params else None
    
    loc = json.loads(params["loc"][0]) if "loc" in params else None
    pplink = params["pplink"][0] if "pplink" in params else None

    hashedpassword = Hash(password)
    
    valid, message = CheckLocation(loc)

    if loc != None and not valid:
        return SetError(response, message)

    valid, message = CheckPassword(password)
   
    if not valid:
        return SetError(response, message)

    valid, message = CheckEmail(email)
    
    if not valid:
        return SetError(response, message)

    dbresult = manager.DatabaseManager.signup(
        {
            "username": username,
            "email":email,
            "password": hashedpassword,
            "name": name,
            "surname": surname,
            "birthdate": birthdate,
            "loc": loc,
            "pplink": pplink,
        }
    )
    
    
    if dbresult == False:
        response["@success"] = "False"
        response["@error"] = "User already exists!"
        return response 
    

    response["@success"] = "True"
    dbresult["@type"] = "User.Object"
    response["@return"] = dbresult
    response["@usertoken"] = manager.RegisterToken(dbresult)

    return response 



def GetMyProfile(manager : ServerManager, userid, params):
    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "User.GetMyProfile"

    dbresult = manager.DatabaseManager.find_user(userid)

    if dbresult == False:
        response["@success"] = "False"
        response["@error"] = "User doesn't exist!"
        return response 
    
    response["@success"] = "True"
    dbresult["@type"] = "User.Object"
    response["@return"] = dbresult

    return response

def GetUserPreview(manager : ServerManager, params):
    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "User.GetPreview"

    userid = params["userid"][0]
    dbresult = manager.DatabaseManager.get_user_preview(userid)

    if dbresult == False:
        response["@success"] = "False"
        response["@error"] = "User doesn't exist!"
        return response 
    
    response["@success"] = "True"
    dbresult["@type"] = "User.Preview"
    response["@return"] = dbresult

    return response



def UpdateProfile(manager : ServerManager, userid, params):
    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "User.UpdateProfile"

    update_d = {}
    
    if "email" in params:
        update_d["email"] = params["email"][0]
            
        valid, message = CheckEmail(update_d["email"])
        
        if not valid:
            return SetError(response, message)
    if "name" in params:
        update_d["name"] = params["name"][0]
    if "surname" in params:
        update_d["surname"] = params["surname"][0]
    if "birthdate" in params:
        update_d["birthdate"] = datetime.fromisoformat(params["birthdate"][0]) 
    if "loc" in params:
        update_d["loc"] = json.loads(params["loc"][0])
         
        valid, message = CheckLocation(update_d["loc"])

        if not valid:
            return SetError(response, message)

    if "pplink" in params:
        update_d["pplink"] = params["pplink"][0]


    dbresult = manager.DatabaseManager.update_profile(userid, update_d)

    if dbresult == False:
        return SetError(response, "Couldn't update profile!")
    
    response["@success"] = "True"
    response["@return"] = manager.DatabaseManager.find_user(userid)
    return response