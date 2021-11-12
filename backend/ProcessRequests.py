from http.server import BaseHTTPRequestHandler
from json import encoder
from typing import Tuple
import urllib
from ServerManager import ServerManager
import json
import string
import unicodedata
import html
import Endpoints


def WriteJSON(self, response):
    bbb = json.dumps(response).encode('utf8')
    self.headers["Content-Length"] = str(len(bbb))
    self.wfile.write(bbb)

def ParsePostBody(self):
    res = self.rfile.read(int(self.headers["Content-Length"]))
    res = res.decode("utf8")
    res = urllib.parse.unquote(res, encoding='ANSI')
    res = html.unescape(res)
    res = urllib.parse.parse_qs(res, encoding='utf8')
    return res

def ProcessRequest(self, manager):
    if ProcessNonTokenRequests(self, manager):
        return
    elif ProcessTokenRequests(self, manager):
        return
    else:
        self.wfile.write("Wrong endpoint!")


def ProcessNonTokenRequests(self, manager):
    if self.path == "/login":
        params = ParsePostBody(self)
        response = Endpoints.User.Login(manager, params)
        WriteJSON(self, response)
        return True
    elif self.path == "/signup":
        params = ParsePostBody(self)
        response = Endpoints.User.SignUp(manager, params)
        WriteJSON(self, response)
        return True
    else:
        return False

def ProcessTokenRequests(self, manager : ServerManager):
    if self.path == "/createcommunity":
        params = ParsePostBody(self)
        usertoken = params["@usertoken"][0]

        if manager.ValidToken(usertoken):
            manager.RefreshToken(usertoken)
            userid = manager.GetUserId(usertoken)
            response = Endpoints.Community.CreateCommunity(manager, userid, params)
            WriteJSON(self, response)
        else:
            WriteJSON(self, 
                {
                    "@context": "https://www.w3.org/ns/activitystreams",
                    "@type": "Community.Create",
                    "@success": "False",
                    "@error":  "Invalid UserToken!",
                }
            )
            
        return True
    else:
        return False