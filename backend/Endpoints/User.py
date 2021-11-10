from http.server import BaseHTTPRequestHandler
from json import encoder
from typing import Tuple
import urllib
from ServerManager import ServerManager
import json
import string
import unicodedata
import html

import re
 
EmailRegex = r'\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}\b'
 
def CheckEmail(email):
    return re.fullmatch(EmailRegex, email)

def Login(self : BaseHTTPRequestHandler, manager : ServerManager, params):
    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "User.Login"

    username = params["username"][0]
    password = params["password"][0]

    dbresult = manager.DatabaseManager.signin(
        {
            "username": username,
            "password": password
        }
    )
    
    response["@success"] = "False" if dbresult == False else "True"
    if dbresult == False:
        response["@error"] = "User not found!"
    else:
        dbresult["@type"] = "User.Object"
        response["@return"] = dbresult
        response["@usertoken"] = manager.RegisterToken(dbresult)

    return response


def SignUp(self : BaseHTTPRequestHandler, manager : ServerManager, params):

    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "User.SignUp"

    username = params["username"][0]
    email = params["email"][0]
    password = params["password"][0]

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
            "password": password
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

#todo check token if it s new son 1 gun