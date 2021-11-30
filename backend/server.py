import pymongo
from pymongo import database
import DB
from http.server import HTTPServer, BaseHTTPRequestHandler
from socketserver import ThreadingMixIn
import urllib.parse
from DB.database import DatabaseManager
import Endpoints
from ServerManager import ServerManager 
import ProcessRequests
import traceback

USE_HTTPS = False

def PrintTraceback(e):
    print('------Start--------')
    print(''.join(traceback.format_exception(type(e), e, e.__traceback__)))
    print('------End--------')

class Handler(BaseHTTPRequestHandler):

    def do_OPTIONS(self):
        self.send_response(200, "ok")
        self.send_header('Access-Control-Allow-Credentials', 'true')
        self.send_header('Access-Control-Allow-Origin', '*')
        self.send_header('Access-Control-Allow-Methods', '*')
        self.send_header("Access-Control-Allow-Headers", "*")
        self.end_headers()
    
    def do_GET(self):
        res = urllib.parse.urlparse(self.path)
        res = urllib.parse.parse_qs(res.query)
        print("params:", res)
        print(self.client_address[0])
        print("path:", self.path)
        self.send_response(200)
        self.end_headers()

        if self.path == "/login":
            self.wfile.write(b"""
    <html>
    <form action="/login" method="POST">
    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username"><br>
    <label for="password">Password:</label><br>
    <input type="text" id="password" name="password">
    <input type="submit" value="Submit">
    </form>

    </html>
            """)
        elif self.path == "/signup":
            self.wfile.write(b"""
    <html>
    <form action="/signup" method="POST">
    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username"><br>
    <label for="email">Email Address:</label><br>
    <input type="text" id="email" name="email"><br>
    <label for="password">Password:</label><br>
    <input type="text" id="password" name="password"><br>

    <label for="name">name:</label><br>
    <input type="text" id="name" name="name"><br>

    <label for="surname">surname:</label><br>
    <input type="text" id="surname" name="surname"><br>

    <label for="city">city:</label><br>
    <input type="text" id="city" name="city"><br>

    <label for="birthdate">birthdate:</label><br>
    <input type="text" id="birthdate" name="birthdate"><br>

    <label for="pplink">pplink:</label><br>
    <input type="text" id="pplink" name="pplink"><br>


    <input type="submit" value="Submit">
    </form>

    </html>
            """)
        elif self.path == "/updateprofile":
            self.wfile.write(b"""
    <html>
    <form action="/updateprofile" method="POST">
    <label for="@usertoken">@usertoken:</label><br>
    <input type="text" id="@usertoken" name="@usertoken"><br>
    <label for="email">Email Address:</label><br>
    <input type="text" id="email" name="email"><br>

    <label for="name">name:</label><br>
    <input type="text" id="name" name="name"><br>

    <label for="surname">surname:</label><br>
    <input type="text" id="surname" name="surname"><br>

    <label for="city">city:</label><br>
    <input type="text" id="city" name="city"><br>

    <label for="birthdate">birthdate:</label><br>
    <input type="text" id="birthdate" name="birthdate"><br>

    <label for="pplink">pplink:</label><br>
    <input type="text" id="pplink" name="pplink"><br>

    <input type="submit" value="Submit">
    </form>

    </html>
            """)
        elif self.path == "/createcommunity":
            self.wfile.write(b"""
    <html>
        <form action="/createcommunity" method="POST">
        <label for="@usertoken">@usertoken:</label><br>
        <input type="text" id="@usertoken" name="@usertoken"><br>
        <label for="communityTitle">communityTitle:</label><br>
        <input type="text" id="communityTitle" name="communityTitle"><br>
        <label for="description">description:</label><br>
        <input type="text" id="description" name="description">
        <input type="submit" value="Submit">
        </form>
    </html>
            """)
        elif self.path == "/subscribetocommunity":
            self.wfile.write(b"""
    <html>
        <form action="/subscribetocommunity" method="POST">
        <label for="@usertoken">@usertoken:</label><br>
        <input type="text" id="@usertoken" name="@usertoken"><br>
        <label for="communityId">communityId:</label><br>
        <input type="text" id="communityId" name="communityId"><br>
        <input type="submit" value="Submit">
        </form>
    </html>
            """)

        elif self.path == "/getallcommunities":
            self.wfile.write(b"""
    <html>
        <form action="/getallcommunities" method="POST">
        <input type="submit" value="Submit">
        </form>
    </html>
            """)
        elif self.path == "/getcommunity":
            self.wfile.write(b"""
    <html>
        <form action="/getcommunity" method="POST">
        <label for="communityId">communityId:</label><br>
        <input type="text" id="communityId" name="communityId"><br>
        <input type="submit" value="Submit">
        </form>
    </html>
            """)
        elif self.path == "/deletecommunity":
            self.wfile.write(b"""
    <html>
        <form action="/deletecommunity" method="POST">
        <label for="@usertoken">@usertoken:</label><br>
        <input type="text" id="@usertoken" name="@usertoken"><br>
        <label for="communityId">communityId:</label><br>
        <input type="text" id="communityId" name="communityId"><br>
        <input type="submit" value="Submit">
        </form>
    </html>
            """)

        
        elif self.path == "/getmyprofile":
            self.wfile.write(b"""
    <html>
        <form action="/getmyprofile" method="POST">
        <label for="@usertoken">@usertoken:</label><br>
        <input type="text" id="@usertoken" name="@usertoken"><br>
        <input type="submit" value="Submit">
        </form>
    </html>
            """)
        
        elif self.path == "/getuserpreview":
            self.wfile.write(b"""
    <html>
        <form action="/getuserpreview" method="POST">
        <label for="userid">userid:</label><br>
        <input type="text" id="userid" name="userid"><br>
        <input type="submit" value="Submit">
        </form>
    </html>
            """)
        elif self.path == "/submitpost":
            self.wfile.write(b"""
    <html>
        <form action="/submitpost" method="POST">
        
        <label for="@usertoken">@usertoken:</label><br>
        <input type="text" id="@usertoken" name="@usertoken"><br>

        <label for="communityId">communityId:</label><br>
        <input type="text" id="communityId" name="communityId"><br>
        
        <label for="title">title:</label><br>
        <input type="text" id="title" name="title"><br>

        <label for="description">description:</label><br>
        <input type="text" id="description" name="description"><br>

        <input type="submit" value="Submit">
        </form>
    </html>
            """)
        elif self.path == "/viewpost":
            self.wfile.write(b"""
    <html>
        <form action="/viewpost" method="POST">
    
        <label for="postId">postId:</label><br>
        <input type="text" id="postId" name="postId"><br>
    
        <input type="submit" value="Submit">
        </form>
    </html>
            """)
        elif self.path == "/deletepost":
            self.wfile.write(b"""
    <html>
        <form action="/deletepost" method="POST">
        
        <label for="@usertoken">@usertoken:</label><br>
        <input type="text" id="@usertoken" name="@usertoken"><br>

        <label for="postId">postId:</label><br>
        <input type="text" id="postId" name="postId"><br>
    
        <input type="submit" value="Submit">
        </form>
    </html>
            """)
        elif self.path == "/unsubscribecommunity":
            self.wfile.write(b"""
    <html>
        <form action="/unsubscribecommunity" method="POST">
        
        <label for="@usertoken">@usertoken:</label><br>
        <input type="text" id="@usertoken" name="@usertoken"><br>

        <label for="communityId">communityId:</label><br>
        <input type="text" id="communityId" name="communityId"><br>
    
        <input type="submit" value="Submit">
        </form>
    </html>
            """)

    def do_POST(self):
        try:
            self.send_response(200)
            ProcessRequests.ProcessRequest(self, manager)
        except Exception as e:
            PrintTraceback(e)
            self.send_response(404)

class ThreadingSimpleServer(ThreadingMixIn, HTTPServer):
        pass

def run():
    server = ThreadingSimpleServer(('0.0.0.0', 8080), Handler)
    if USE_HTTPS:
        import ssl
        server.socket = ssl.wrap_socket(server.socket, keyfile='./key.pem', certfile='./cer.pem', server_side=True)
    server.serve_forever()

databasemanager = DatabaseManager()
manager = ServerManager(databasemanager)

run()

#sdA12323
# Token
# Activity Streams

