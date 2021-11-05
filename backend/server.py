import pymongo
import DB
from http.server import HTTPServer, BaseHTTPRequestHandler
from socketserver import ThreadingMixIn
import urllib.parse

USE_HTTPS = False

class Handler(BaseHTTPRequestHandler):
    
    def do_GET(self):
        res = urllib.parse.urlparse(self.path)
        res = urllib.parse.parse_qs(res.query)
        print("res:", res)
        print(self.client_address[0])
        print("path:", self.path)
        self.send_response(200)
        self.end_headers()
        self.wfile.write(b"Maybe I am healthy!")

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

