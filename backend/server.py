import pymongo
import DB
from http.server import HTTPServer, BaseHTTPRequestHandler
from socketserver import ThreadingMixIn
import urllib.parse
import Endpoints

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
        self.wfile.write(b"""
<html>
<form action="/login" method="POST">
  <label for="fname">First name:</label><br>
  <input type="text" id="fname" name="fname"><br>
  <label for="lname">Last name:</label><br>
  <input type="text" id="lname" name="lname">
  <input type="submit" value="Submit">
</form>

</html>
        """)

    def do_POST(self):
        if self.path == "/login":
            Endpoints.User.Login(self)
            return



class ThreadingSimpleServer(ThreadingMixIn, HTTPServer):
        pass

def run():
    server = ThreadingSimpleServer(('0.0.0.0', 80), Handler)
    if USE_HTTPS:
        import ssl
        server.socket = ssl.wrap_socket(server.socket, keyfile='./key.pem', certfile='./cer.pem', server_side=True)
    server.serve_forever()

run()


# 1 signup
# 1 login -> Token (kaç saat aktif)

# Signup(d) d["username"] d["password"] # True veya False
# Login(d) d["username"] d["password"] returns d["userid"]

# Token
# Activity Streams

