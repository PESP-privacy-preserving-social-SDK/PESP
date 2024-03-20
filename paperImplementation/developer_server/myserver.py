from http.server import BaseHTTPRequestHandler, HTTPServer
import time
from io import BytesIO

hostName = ""
serverPort = 8000

class MyServer(BaseHTTPRequestHandler):
    fbloggedIn = False
    
    def do_GET(self):
        self.send_response(200)
        self.send_header("Content-type", "text/html")
        self.send_header("Access-Control-Allow-Headers", "*")
        self.end_headers()
        self.wfile.write(bytes("<html><head><title>https://pythonbasics.org</title></head>", "utf-8"))
        self.wfile.write(bytes("<p>Request: %s</p>" % self.path, "utf-8"))
        self.wfile.write(bytes("<body>", "utf-8"))
        self.wfile.write(bytes("<p>This is an example web server.</p>", "utf-8"))
        self.wfile.write(bytes("</body></html>", "utf-8"))
    def do_POST(self):
        content_length = int(self.headers['Content-Length'])
        body = self.rfile.read(content_length)
        self.send_response(200)
        self.send_header("Access-Control-Allow-Headers", "*")
        self.end_headers()
        response = BytesIO()
        response.write(b'This is POST request. ')
        response.write(b'Received: ')
        response.write(body)
        self.wfile.write(response.getvalue())
    def do_OPTIONS(self):
        # content_length = int(self.headers['Content-Length'])
        # body = self.rfile.read(content_length)
        self.send_response(200)
        self.send_header("Access-Control-Allow-Headers", "*")
        self.end_headers()
        response = BytesIO()
        response.write(b'This is OPTIONS request. ')
        response.write(b'Received: ')
        response.write(b"caonima")
        self.wfile.write(response.getvalue())

if __name__ == "__main__":        
    webServer = HTTPServer((hostName, serverPort), MyServer)
    print("Server started http://%s:%s" % (hostName, serverPort))

    try:
        webServer.serve_forever()
    except KeyboardInterrupt:
        pass

    webServer.server_close()
    print("Server stopped.")