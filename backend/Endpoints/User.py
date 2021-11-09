from http.server import BaseHTTPRequestHandler
import urllib

def Login(self : BaseHTTPRequestHandler):
    res = self.rfile.read(int(self.headers["Content-Length"])).decode("utf8")
    res = urllib.parse.parse_qs(res)
    print("params:", res)
    self.wfile.write(b"Success!")