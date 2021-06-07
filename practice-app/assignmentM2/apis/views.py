from typing import List
from django.http.request import HttpRequest
from django.shortcuts import render
from django.http import HttpResponse
import json

# Create your views here.
import urllib

def examplepage(request):
    return HttpResponse("<h1>API Example Page!<h1>")


def getflag(request:HttpRequest):
    if request.method == "GET" and "country" in request.GET:
        return HttpResponse("<img src=\"https://www.countryflags.io/" + request.GET["country"] + "/flat/64.png\">")
    elif request.method == "POST" and "country" in request.POST:
        return HttpResponse("<img src=\"https://www.countryflags.io/" + request.POST["country"] + "/flat/64.png\">")

    return HttpResponse("""<img src="https://www.countryflags.io/be/flat/64.png">""")


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


def getmealrecipe(request:HttpRequest):
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
