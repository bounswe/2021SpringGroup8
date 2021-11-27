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
from datetime import datetime


def default(obj):
    if isinstance(obj, datetime):
        return { '_isoformat': obj.isoformat() }
    raise TypeError('...')

def object_hook(obj):
    _isoformat = obj.get('_isoformat')
    if _isoformat is not None:
        return datetime.fromisoformat(_isoformat)
    return obj

def WriteJSON(self, response):
    bbb = json.dumps(response, default=default).encode('utf8')
    self.send_header('Content-Length', str(len(bbb)))
    self.send_header('Access-Control-Allow-Credentials', 'true')
    self.send_header('Access-Control-Allow-Origin', '*')
    self.send_header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS')
    self.send_header("Access-Control-Allow-Headers", "X-Requested-With, Content-type")
    self.end_headers()
    self.wfile.write(bbb)
    #print(d == json.loads(s, object_hook=object_hook))

def ParsePostBody(self):
    res = self.rfile.read(int(self.headers["Content-Length"]))
    res = res.decode("utf8")
    res = urllib.parse.unquote(res, encoding='latin1')
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
    elif self.path == "/getallcommunities":
        params = ParsePostBody(self)
        response = Endpoints.Community.GetAllCommunities(manager, params)
        WriteJSON(self, response)
        return True
    elif self.path == "/getcommunity":
        params = ParsePostBody(self)
        response = Endpoints.Community.GetCommunity(manager, params)
        WriteJSON(self, response)
        return True
    elif self.path == "/getuserpreview":
        params = ParsePostBody(self)
        response = Endpoints.User.GetUserPreview(manager, params)
        WriteJSON(self, response)
        return True
    elif self.path == "/viewpost":
        params = ParsePostBody(self)
        response = Endpoints.Post.View(manager, params)
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
    elif self.path == "/subscribetocommunity":
        params = ParsePostBody(self)
        usertoken = params["@usertoken"][0]

        if manager.ValidToken(usertoken):
            manager.RefreshToken(usertoken)
            userid = manager.GetUserId(usertoken)
            response = Endpoints.Community.SubscribeToCommunity(manager, userid, params)
            WriteJSON(self, response)
        else:
            WriteJSON(self, 
                {
                    "@context": "https://www.w3.org/ns/activitystreams",
                    "@type": "Community.SubscribeTo",
                    "@success": "False",
                    "@error":  "Invalid UserToken!",
                }
            )
            
        return True

    elif self.path == "/deletecommunity":
        params = ParsePostBody(self)
        usertoken = params["@usertoken"][0]

        if manager.ValidToken(usertoken):
            manager.RefreshToken(usertoken)
            userid = manager.GetUserId(usertoken)
            response = Endpoints.Community.DeleteCommunity(manager, userid, params)
            WriteJSON(self, response)
        else:
            WriteJSON(self, 
                {
                    "@context": "https://www.w3.org/ns/activitystreams",
                    "@type": "Community.Delete",
                    "@success": "False",
                    "@error":  "Invalid UserToken!",
                }
            )
            
        return True
    elif self.path == "/getmyprofile":
        params = ParsePostBody(self)
        usertoken = params["@usertoken"][0]

        if manager.ValidToken(usertoken):
            manager.RefreshToken(usertoken)
            userid = manager.GetUserId(usertoken)
            response = Endpoints.User.GetMyProfile(manager, userid, params)
            WriteJSON(self, response)
        else:
            WriteJSON(self, 
                {
                    "@context": "https://www.w3.org/ns/activitystreams",
                    "@type": "User.GetMyProfile",
                    "@success": "False",
                    "@error":  "Invalid UserToken!",
                }
            )
            
        return True
    elif self.path == "/submitpost":
        params = ParsePostBody(self)
        usertoken = params["@usertoken"][0]

        if manager.ValidToken(usertoken):
            manager.RefreshToken(usertoken)
            userid = manager.GetUserId(usertoken)
            response = Endpoints.Post.Submit(manager, userid, params)
            WriteJSON(self, response)
        else:
            WriteJSON(self, 
                {
                    "@context": "https://www.w3.org/ns/activitystreams",
                    "@type": "Post.Submit",
                    "@success": "False",
                    "@error":  "Invalid UserToken!",
                }
            )
            
        return True
        
    elif self.path == "/deletepost":
        params = ParsePostBody(self)
        usertoken = params["@usertoken"][0]

        if manager.ValidToken(usertoken):
            manager.RefreshToken(usertoken)
            userid = manager.GetUserId(usertoken)
            response = Endpoints.Post.Delete(manager, userid, params)
            WriteJSON(self, response)
        else:
            WriteJSON(self, 
                {
                    "@context": "https://www.w3.org/ns/activitystreams",
                    "@type": "Post.Delete",
                    "@success": "False",
                    "@error":  "Invalid UserToken!",
                }
            )
            
        return True
        
    elif self.path == "/unsubscribecommunity":
        params = ParsePostBody(self)
        usertoken = params["@usertoken"][0]

        if manager.ValidToken(usertoken):
            manager.RefreshToken(usertoken)
            userid = manager.GetUserId(usertoken)
            response = Endpoints.Community.Unsubscribe(manager, userid, params)
            WriteJSON(self, response)
        else:
            WriteJSON(self, 
                {
                    "@context": "https://www.w3.org/ns/activitystreams",
                    "@type": "Community.Unsubscribe",
                    "@success": "False",
                    "@error":  "Invalid UserToken!",
                }
            )
            
        return True
    elif self.path == "/updateprofile":
        params = ParsePostBody(self)
        usertoken = params["@usertoken"][0]

        if manager.ValidToken(usertoken):
            manager.RefreshToken(usertoken)
            userid = manager.GetUserId(usertoken)
            response = Endpoints.User.UpdateProfile(manager, userid, params)
            WriteJSON(self, response)
        else:
            WriteJSON(self, 
                {
                    "@context": "https://www.w3.org/ns/activitystreams",
                    "@type": "User.UpdateProfile",
                    "@success": "False",
                    "@error":  "Invalid UserToken!",
                }
            )
            
        return True
    else: 
        return False



