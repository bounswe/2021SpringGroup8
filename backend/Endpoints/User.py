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
 
EmailRegex = r'\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}\b'
 
def CheckEmail(email):
    return re.fullmatch(EmailRegex, email)

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
    else:
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
    hashedpassword = Hash(password)
    
    if not (len(password) >= 8 and len(password) <= 16):
        response["@success"] = "False"
        response["@error"] = "Password Length must be >= 8, <= 16!"
        return response

    if sum(1 for c in password if c.isupper()) == 0:
        response["@success"] = "False"
        response["@error"] = "Password must contain at least 1 upper case letter!"
        return response

    if not CheckEmail(email):
        response["@success"] = "False"
        response["@error"] = "Email is not valid!"
        return response


    dbresult = manager.DatabaseManager.signup(
        {
            "username": username,
            "email":email,
            "password": hashedpassword
        }
    )
    
    
    if dbresult == False:
        response["@success"] = "False"
        response["@error"] = "User already exists!"
    else:
        response["@success"] = "True"
        dbresult["@type"] = "User.Object"
        response["@return"] = dbresult

    return response 
