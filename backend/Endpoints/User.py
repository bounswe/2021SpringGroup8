from http.server import BaseHTTPRequestHandler
from json import encoder
from typing import Tuple
import urllib
from ServerManager import ServerManager
import json
import string
import unicodedata
import html


def WriteJSON(self, response):
    self.wfile.write(json.dumps(response).encode('utf8'))

def ParsePostBody(self):
    res = self.rfile.read(int(self.headers["Content-Length"]))
    res = res.decode("utf8")
    res = urllib.parse.unquote(res, encoding='ANSI')
    res = html.unescape(res)
    res = urllib.parse.parse_qs(res, encoding='utf8')
    return res

def Login(self : BaseHTTPRequestHandler, manager : ServerManager):
    res = ParsePostBody(self)

    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "User.Login"

    username = res["username"][0]
    password = res["password"][0]

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

    WriteJSON(self, response)


def SignUp(self : BaseHTTPRequestHandler, manager : ServerManager):
    res = ParsePostBody(self)

    response = {}
    response["@context"] = "https://www.w3.org/ns/activitystreams"
    response["@type"] = "User.SignUp"

    username = res["username"][0]
    email = res["email"][0]
    password = res["password"][0]

    if not (len(password) >= 8 and len(password) <= 16):
        response["@success"] = "False"
        response["@error"] = "Password Length must be >= 8, <= 16!"
        WriteJSON(self, response)
        return

    if sum(1 for c in password if c.isupper()) == 0:
        response["@success"] = "False"
        response["@error"] = "Password must contain at least 1 upper case letter!"
        WriteJSON(self, response)
        return

    dbresult = manager.DatabaseManager.signup(
        {
            "username": username,
            "email":email,
            "password": password
        }
    )
    
    
    response["@success"] = "False" if dbresult == False else "True"
    if dbresult == False:
        response["@error"] = "User already exists!"
    else:
        dbresult["@type"] = "User.Object"
        response["@return"] = dbresult

    WriteJSON(self, response)

#todo check token if it s new son 1 gun