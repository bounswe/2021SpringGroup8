from typing import List
from django.http.request import HttpRequest
from django.shortcuts import render
from django.http import HttpResponse
import numpy as np
# Create your views here.
import urllib
import io
import base64

def gethtmlimage(url):
    from PIL import Image
    req = urllib.request.Request(url,headers={
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.47 Safari/537.36'
    })

    im = Image.open(urllib.request.urlopen(req))
    rawBytes = io.BytesIO()
    im.save(rawBytes, "PNG")
    rawBytes.seek(0)  # return to the start of the file
    basestr = base64.b64encode(rawBytes.read()).decode("utf-8")
    #print(base64.b64encode(rawBytes.read()), "|||", basestr)
    ss = """<img src="data:image/png;base64, """ + basestr + """" />"""
    return ss

def examplepage(request):
    return HttpResponse("<h1>API Example Page!<h1>")

def getflag(request:HttpRequest):
    if request.method == "GET" and "country" in request.GET:
        return HttpResponse(gethtmlimage("https://www.countryflags.io/" + request.GET["country"] + "/flat/64.png"))
    elif request.method == "POST" and "country" in request.POST:
        return HttpResponse(gethtmlimage("https://www.countryflags.io/" + request.POST["country"] + "/flat/64.png"))

    return HttpResponse(gethtmlimage("https://www.countryflags.io/be/flat/64.png"))

def getcurrencies(request:HttpRequest):
    url = "https://free.currconv.com/api/v7/convert?q=TRY_USD&compact=ultra&apiKey=55d560f06e174022b414"
   
    if request.method == "GET" and "from" in request.GET and "to" in request.GET:
        url = "https://free.currconv.com/api/v7/convert?q=" + request.GET["from"] + "_" + request.GET["to"] + "&compact=ultra&apiKey=55d560f06e174022b414"
    elif request.method == "POST" and "from" in request.POST and "to" in request.POST:
        url = "https://free.currconv.com/api/v7/convert?q=" + request.POST["from"] + "_" + request.POST["to"] + "&compact=ultra&apiKey=55d560f06e174022b414"
    
    req = urllib.request.Request(url)
    with urllib.request.urlopen(req) as response:
        page = response.read().decode("utf8")
        num = (page[page.find(":")+1:-1])
        return HttpResponse(num)
