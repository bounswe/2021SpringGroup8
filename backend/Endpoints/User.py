from http.server import BaseHTTPRequestHandler
from json import encoder
from typing import Tuple
import urllib
from ServerManager import ServerManager
import json
import string
import unicodedata
import html


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