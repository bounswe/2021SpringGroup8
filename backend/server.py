import pymongo
import DB
from http.server import HTTPServer, BaseHTTPRequestHandler
from socketserver import ThreadingMixIn

USE_HTTPS = False

class Handler(BaseHTTPRequestHandler):
    
    def do_GET(self):
        print(self.client_address[0])
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

