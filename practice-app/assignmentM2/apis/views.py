from typing import List
from django.http.request import HttpRequest
from django.shortcuts import render
from django.http import HttpResponse
<<<<<<< HEAD
<<<<<<< HEAD
from . import apps
=======
import json

>>>>>>> using meal recipe api is done
=======
import numpy as np
>>>>>>> Update views.py
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


<<<<<<< HEAD

def registeruser(username, password):
     return apps.collection_name.insert_one({"username" : username, "password":password})


def searchuser(username):
    return apps.collection_name.find_one({"username":username})

=======
>>>>>>> using meal recipe api is done
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


def getrandommealrecipe(request:HttpRequest):
    url = "https://www.themealdb.com/api/json/v1/1/random.php"

    req = urllib.request.Request(url)
    with urllib.request.urlopen(req) as response:
        page = response.read().decode("utf8")
        data = json.loads(json.loads(json.dumps(page)))

        meal_name = data['meals'][0]['strMeal']
        meal_category = data['meals'][0]['strCategory']
        meal_area = data['meals'][0]['strArea']
        meal_recipe = data['meals'][0]['strInstructions']

        context = ['meal_name: ', meal_name, '<br>meal_category: ', meal_category,
                   '<br>meal_area: ', meal_area, '<br>meal_recipe: ', meal_recipe]

        return HttpResponse(context)


def getmealrecipebyname(request: HttpRequest):
    url = "https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata"

    if request.method == "GET" and "s" in request.GET:
        url = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + request.GET["s"]
    elif request.method == "POST" and "s" in request.POST:
        url = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + request.POST["s"]

    req = urllib.request.Request(url)
    with urllib.request.urlopen(req) as response:
        page = response.read().decode("utf8")
        data = json.loads(json.loads(json.dumps(page)))
        data = data['meals']
        context = []
        if type(data) is list:
            for x in data:
                meal_name = x['strMeal']
                meal_category = x['strCategory']
                meal_area = x['strArea']
                meal_recipe = x['strInstructions']
                temp = ['meal_name: ', meal_name, '<br>meal_category: ', meal_category,
                       '<br>meal_area: ', meal_area, '<br>meal_recipe: ', meal_recipe]
                context.extend(temp)
                context.append('<br><br>')
        else:
            return HttpResponse("null")

        return HttpResponse(context)
