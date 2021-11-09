import pymongo
from pymongo import database
import DB
from http.server import HTTPServer, BaseHTTPRequestHandler
from socketserver import ThreadingMixIn
import urllib.parse
from DB.database import DatabaseManager
import Endpoints
from ServerManager import ServerManager 

USE_HTTPS = False

class Handler(BaseHTTPRequestHandler):
    
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
    <input type="text" id="password" name="password">
    <input type="submit" value="Submit">
    </form>

    </html>
            """)

    def do_POST(self):
        if self.path == "/login":
            Endpoints.User.Login(self, manager)
            return
        elif self.path == "/signup":
            Endpoints.User.SignUp(self, manager)
            return
        else:
            self.wfile.write("Wrong endpoint!")



class ThreadingSimpleServer(ThreadingMixIn, HTTPServer):
        pass

def run():
    server = ThreadingSimpleServer(('0.0.0.0', 80), Handler)
    if USE_HTTPS:
        import ssl
        server.socket = ssl.wrap_socket(server.socket, keyfile='./key.pem', certfile='./cer.pem', server_side=True)
    server.serve_forever()


databasemanager = DatabaseManager()
manager = ServerManager(databasemanager)

run()


# 1 signup
# 1 login -> Token (kaç saat aktif)

# Signup(d) d["username"] d["password"] # True veya False
# Login(d) d["username"] d["password"] returns d["userid"]

# Token
# Activity Streams

