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
    self.wfile.write(json.dumps(response).encode('utf8'))

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
        response = Endpoints.User.Login(self, manager, params)
        WriteJSON(self, response)
        return True
    elif self.path == "/signup":
        params = ParsePostBody(self)
        response = Endpoints.User.SignUp(self, manager, params)
        WriteJSON(self, response)
        return True
    else:
        return False

def ProcessTokenRequests(self, manager):
    return False