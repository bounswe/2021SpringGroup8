<<<<<<< HEAD
from typing import List
from django.http.request import HttpRequest
from django.shortcuts import render
from django.http import HttpResponse
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
from . import apps
<<<<<<< HEAD
=======
=======
from .models import Meal
>>>>>>> The funcionality of adding data to database for mealrecipes is added
import json
=======
import requests
>>>>>>> second commit

>>>>>>> using meal recipe api is done
=======
import numpy as np
>>>>>>> Update views.py
# Create your views here.
<<<<<<< HEAD
=======
from . import apps
import json
import numpy as np
>>>>>>> I add url path and write unittest for weather api
import urllib
import io
import base64
from math import sin, cos, sqrt, atan2, radians
<<<<<<< HEAD

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
=======
from .forms import EventCreateForm
from .models import Event
>>>>>>> second commit
=======

# Event model
=======
>>>>>>> I add url path and write unittest for weather api
from .forms import EventCreateForm
from .models import Event
import requests
>>>>>>> second draft for weather api


def gethtmlimage(url):
    from PIL import Image
    req = urllib.request.Request(url, headers={
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.47 Safari/537.36'
    })

    im = Image.open(urllib.request.urlopen(req))
    rawBytes = io.BytesIO()
    im.save(rawBytes, "PNG")
    rawBytes.seek(0)  # return to the start of the file
    basestr = base64.b64encode(rawBytes.read()).decode("utf-8")
    # print(base64.b64encode(rawBytes.read()), "|||", basestr)
    ss = """<img src="data:image/png;base64, """ + basestr + """" />"""
    return ss


def examplepage(request):
    return HttpResponse("<h1>API Example Page!<h1>")


def getflag(request: HttpRequest):
    if request.method == "GET" and "country" in request.GET:
        return HttpResponse(gethtmlimage("https://www.countryflags.io/" + request.GET["country"] + "/flat/64.png"))
    elif request.method == "POST" and "country" in request.POST:
        return HttpResponse(gethtmlimage("https://www.countryflags.io/" + request.POST["country"] + "/flat/64.png"))

    return HttpResponse(gethtmlimage("https://www.countryflags.io/be/flat/64.png"))


def registeruser(username, password):
    return apps.collection_name.insert_one({"username": username, "password": password})


def searchuser(username):
    return apps.collection_name.find_one({"username": username})


def getcurrencies(request: HttpRequest):
    url = "https://free.currconv.com/api/v7/convert?q=TRY_USD&compact=ultra&apiKey=55d560f06e174022b414"

    if request.method == "GET" and "from" in request.GET and "to" in request.GET:
        url = "https://free.currconv.com/api/v7/convert?q=" + request.GET["from"] + "_" + request.GET[
            "to"] + "&compact=ultra&apiKey=55d560f06e174022b414"
    elif request.method == "POST" and "from" in request.POST and "to" in request.POST:
        url = "https://free.currconv.com/api/v7/convert?q=" + request.POST["from"] + "_" + request.POST[
            "to"] + "&compact=ultra&apiKey=55d560f06e174022b414"

    req = urllib.request.Request(url)
    with urllib.request.urlopen(req) as response:
        page = response.read().decode("utf8")
        return HttpResponse(page)


def getrandommealrecipe(request: HttpRequest):
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


def finddistance(request: HttpRequest):
    url = "https://api.opencagedata.com/geocode/v1/json?key=2a41d6ab37f44a2cb85b254b57123393&q="

    if request.method == "GET" and "country_from" in request.GET and "city_from" in request.GET and "county_from" in request.GET and "country_to" in request.GET and "city_to" in request.GET and "county_to" in request.GET:
        url1 = url + request.GET["county_from"] + '%2C+' + request.GET["city_from"] + '%2C+' + request.GET[
            "country_from"] + '&pretty=1'
        url2 = url + request.GET["county_to"] + '%2C+' + request.GET["city_to"] + '%2C+' + request.GET[
            "country_to"] + '&pretty=1'
        with urllib.request.urlopen(url1) as response1:
            string = response1.read().decode('utf-8')
            res1 = json.loads(string)
            lat1 = res1["results"][0]["geometry"]["lat"]
            lng1 = res1["results"][0]["geometry"]["lng"]
        with urllib.request.urlopen(url2) as response2:
            string = response2.read().decode('utf-8')
            res2 = json.loads(string)
            lat2 = res2["results"][0]["geometry"]["lat"]
            lng2 = res2["results"][0]["geometry"]["lng"]
    elif request.method == "POST" and "country_from" in request.POST and "city_from" in request.POST and "county_from" in request.POST and "country_to" in request.POST and "city_to" in request.POST and "county_to" in request.POST:
        url1 = url + request.POST["county_from"] + '%2C+' + request.POST["city_from"] + '%2C+' + request.POST[
            "country_from"] + '&pretty=1'
        url2 = url + request.POST["county_to"] + '%2C+' + request.POST["city_to"] + '%2C+' + request.POST[
            "country_to"] + '&pretty=1'
        with urllib.request.urlopen(url1) as response1:
            string = response1.read().decode('utf-8')
            res1 = json.loads(string)
            lat1 = res1["results"][0]["geometry"]["lat"]
            lng1 = res1["results"][0]["geometry"]["lng"]
        with urllib.request.urlopen(url2) as response2:
            string = response2.read().decode('utf-8')
            res2 = json.loads(string)
            lat2 = res2["results"][0]["geometry"]["lat"]
            lng2 = res2["results"][0]["geometry"]["lng"]

    rlat1 = radians(lat1)
    rlon1 = radians(lng1)
    rlat2 = radians(lat2)
    rlon2 = radians(lng2)

    dlon = rlon2 - rlon1
    dlat = rlat2 - rlat1

    a = sin(dlat / 2) ** 2 + cos(rlat1) * cos(rlat2) * sin(dlon / 2) ** 2
    c = 2 * atan2(sqrt(a), sqrt(1 - a))
    distance = 6373.0 * c

    return HttpResponse(str(distance))


def create_event(request):
    weather = None
    form = EventCreateForm()
    full_data = ''
    if request.method == 'POST':
        form = EventCreateForm(request.POST)
        if form.is_valid():
            full_data = form.cleaned_data
            weather = getWeather(full_data['city_name'])
            Event.objects.create(**full_data)
            form = EventCreateForm()

<<<<<<< HEAD
def getflag(request:HttpRequest):
    if request.method == "GET" and "country" in request.GET:
        return HttpResponse(gethtmlimage("https://www.countryflags.io/" + request.GET["country"] + "/flat/64.png"))
    elif request.method == "POST" and "country" in request.POST:
        return HttpResponse(gethtmlimage("https://www.countryflags.io/" + request.POST["country"] + "/flat/64.png"))

    return HttpResponse(gethtmlimage("https://www.countryflags.io/be/flat/64.png"))


<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> The funcionality of adding data to database for mealrecipes is added
def registeruser(username, password):
     return apps.collection_name.insert_one({"username" : username, "password":password})


def searchuser(username):
    return apps.collection_name.find_one({"username":username})

<<<<<<< HEAD
=======
>>>>>>> using meal recipe api is done
=======

>>>>>>> The funcionality of adding data to database for mealrecipes is added
def getcurrencies(request:HttpRequest):
    url = "https://free.currconv.com/api/v7/convert?q=TRY_USD&compact=ultra&apiKey=55d560f06e174022b414"
   
    if request.method == "GET" and "from" in request.GET and "to" in request.GET:
        url = "https://free.currconv.com/api/v7/convert?q=" + request.GET["from"] + "_" + request.GET["to"] + "&compact=ultra&apiKey=55d560f06e174022b414"
    elif request.method == "POST" and "from" in request.POST and "to" in request.POST:
        url = "https://free.currconv.com/api/v7/convert?q=" + request.POST["from"] + "_" + request.POST["to"] + "&compact=ultra&apiKey=55d560f06e174022b414"
    
    req = urllib.request.Request(url)
    with urllib.request.urlopen(req) as response:
        page = response.read().decode("utf8")
<<<<<<< HEAD
        num = (page[page.find(":")+1:-1])
        return HttpResponse(num)


def getrandommealrecipe(request:HttpRequest):
    url = "https://www.themealdb.com/api/json/v1/1/random.php"

    req = urllib.request.Request(url)
    with urllib.request.urlopen(req) as response:
        page = response.read().decode("utf8")
        data = json.loads(json.loads(json.dumps(page)))

        meal = Meal()
        meal.name = data['meals'][0]['strMeal']
        meal.category = data['meals'][0]['strCategory']
        meal.area = data['meals'][0]['strArea']
        meal.recipe = data['meals'][0]['strInstructions']

        context = ['meal_name: ', meal.name, '<br>meal_category: ', meal.category,
                   '<br>meal_area: ', meal.area, '<br>meal_recipe: ', meal.recipe]

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
                meal = Meal()
                meal.name = x['strMeal']
                meal.category = x['strCategory']
                meal.area = x['strArea']
                meal.recipe = x['strInstructions']
                meal.save()
                temp = ['meal_name: ', meal.name, '<br>meal_category: ', meal.category,
                       '<br>meal_area: ', meal.area, '<br>meal_recipe: ', meal.recipe]
                context.extend(temp)
                context.append('<br><br>')
        else:
            return HttpResponse("null")

        return HttpResponse(context)
=======
       
<<<<<<< HEAD
        return HttpResponse(page)
>>>>>>> Update views.py
=======
       
def finddistance(request:HttpRequest):
    url = "https://api.opencagedata.com/geocode/v1/json?key=2a41d6ab37f44a2cb85b254b57123393&q="
    
    if request.method == "GET" and "country_from" in request.GET and "city_from" in request.GET and "county_from" in request.GET and "country_to" in request.GET and "city_to" in request.GET and "county_to" in request.GET:
        url1 = url + request.GET["county_from"] + '%2C+' + request.GET["city_from"] + '%2C+' + request.GET["country_from"] + '&pretty=1'
        url2 = url + request.GET["county_to"] + '%2C+' + request.GET["city_to"] + '%2C+' + request.GET["country_to"] + '&pretty=1'
        with urllib.request.urlopen(url1) as response1:
            string = response1.read().decode('utf-8')
            res1 = json.loads(string)
            lat1 = res1["results"][0]["geometry"]["lat"]
            lng1 = res1["results"][0]["geometry"]["lng"]
        with urllib.request.urlopen(url2) as response2:
            string = response2.read().decode('utf-8')
            res2 = json.loads(string)
            lat2 = res2["results"][0]["geometry"]["lat"]
            lng2 = res2["results"][0]["geometry"]["lng"]
    elif request.method == "POST" and "country_from" in request.POST and "city_from" in request.POST and "county_from" in request.POST and "country_to" in request.POST and "city_to" in request.POST and "county_to" in request.POST:
        url1 = url + request.POST["county_from"] + '%2C+' + request.POST["city_from"] + '%2C+' + request.POST["country_from"] + '&pretty=1'
        url2 = url + request.POST["county_to"] + '%2C+' + request.POST["city_to"] + '%2C+' + request.POST["country_to"] + '&pretty=1'
        with urllib.request.urlopen(url1) as response1:
            string = response1.read().decode('utf-8')
            res1 = json.loads(string)
            lat1 = res1["results"][0]["geometry"]["lat"]
            lng1 = res1["results"][0]["geometry"]["lng"]
        with urllib.request.urlopen(url2) as response2:
            string = response2.read().decode('utf-8')
            res2 = json.loads(string)
            lat2 = res2["results"][0]["geometry"]["lat"]
            lng2 = res2["results"][0]["geometry"]["lng"]

    rlat1 = radians(lat1)
    rlon1 = radians(lng1)
    rlat2 = radians(lat2)
    rlon2 = radians(lng2)

    dlon = rlon2 - rlon1
    dlat = rlat2 - rlat1

    a = sin(dlat / 2)**2 + cos(rlat1) * cos(rlat2) * sin(dlon / 2)**2
    c = 2 * atan2(sqrt(a), sqrt(1 - a))
    distance = 6373.0*c

    return HttpResponse(str(distance))
>>>>>>> added distance api
=======
    context = {
        'weather': weather,
        'form': form,
        'data': full_data
    }
    return render(request, 'event_create.html', context)


<<<<<<< HEAD
<<<<<<< HEAD
def examplepage(request):
    return HttpResponse("<h1>API Example Page!<h1>")
>>>>>>> second commit
=======
>>>>>>> second draft for weather api
=======
def getWeather(city_name):
    weather = None
    url = 'http://api.openweathermap.org/data/2.5/weather?q=' + city_name + '&APPID=a37330855b4d53528b42c62007788711'
    response = ''
    try:
        response = requests.get(url)
    except requests.exceptions.RequestException as e:
        pass
    if response.ok:
        data = response.json()
        weather = data['weather'][0]['main']
    return weather
>>>>>>> I add url path and write unittest for weather api
=======
from typing import List
from django.http.request import HttpRequest
from django.shortcuts import render
from django.http import HttpResponse
from . import apps
from .models import Meal
import json
import numpy as np
import urllib
import io
import base64
from math import sin, cos, sqrt, atan2, radians
from .forms import EventCreateForm
from .models import Event,User
import requests
from PIL import Image
from decouple import config #pip install python-decouple

CURRENCY_API_KEY=config('CURRENCY_API_KEY')
DISTANCE_API_KEY=config('DISTANCE_API_KEY')

def gethtmlimage(url):
    req = urllib.request.Request(url, headers={
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.47 Safari/537.36'
    })

    im = Image.open(urllib.request.urlopen(req))
    rawBytes = io.BytesIO()
    im.save(rawBytes, "PNG")
    rawBytes.seek(0)  # return to the start of the file
    basestr = base64.b64encode(rawBytes.read()).decode("utf-8")
    # print(base64.b64encode(rawBytes.read()), "|||", basestr)
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



def registeruser(request:HttpRequest):
    if request.method == "POST":
        name = request.POST['name']
        username = request.POST['username']
        email = request.POST['email']
        password = request.POST['password']
        id= apps.collection_name.insert_one({"name" : name,"username" : username,"email" : email, "password":password})
        return HttpResponse("User registered with id "+ id)


def searchuser(request:HttpRequest, un):
    if request.method == "GET":
        user_name = User.objects.get(username=un)
        response = json.dumps([{'Name': user_name.name, 'Username': user_name.username, 'Active User': user_name.isActive, 'Email':user_name.email}])
    return HttpResponse(response, content_type='text/json')


def getcurrencies(request:HttpRequest):
    url = "https://free.currconv.com/api/v7/convert?q=TRY_USD&compact=ultra&apiKey="+CURRENCY_API_KEY
   
    if request.method == "GET" and "from" in request.GET and "to" in request.GET:
        url = "https://free.currconv.com/api/v7/convert?q=" + request.GET["from"] + "_" + request.GET["to"] + "&compact=ultra&apiKey=" +CURRENCY_API_KEY
    elif request.method == "POST" and "from" in request.POST and "to" in request.POST:
        url = "https://free.currconv.com/api/v7/convert?q=" + request.POST["from"] + "_" + request.POST["to"] + "&compact=ultra&apiKey=" + CURRENCY_API_KEY

    req = urllib.request.Request(url)
    with urllib.request.urlopen(req) as response:
        page = response.read().decode("utf8")
        return HttpResponse(page)


def getrandommealrecipe(request:HttpRequest):
    url = "https://www.themealdb.com/api/json/v1/1/random.php"

    req = urllib.request.Request(url)
    with urllib.request.urlopen(req) as response:
        page = response.read().decode("utf8")
        data = json.loads(json.loads(json.dumps(page)))

        meal = Meal()
        meal.name = data['meals'][0]['strMeal']
        meal.category = data['meals'][0]['strCategory']
        meal.area = data['meals'][0]['strArea']
        meal.recipe = data['meals'][0]['strInstructions']

        context = ['meal_name: ', meal.name, '<br>meal_category: ', meal.category,
                   '<br>meal_area: ', meal.area, '<br>meal_recipe: ', meal.recipe]

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
                meal = Meal()
                meal.name = x['strMeal']
                meal.category = x['strCategory']
                meal.area = x['strArea']
                meal.recipe = x['strInstructions']
                meal.save()
                temp = ['meal_name: ', meal.name, '<br>meal_category: ', meal.category,
                       '<br>meal_area: ', meal.area, '<br>meal_recipe: ', meal.recipe]

                context.extend(temp)
                context.append('<br><br>')
        else:
            return HttpResponse("null")

        return HttpResponse(context)


       
       
def finddistance(request:HttpRequest):
    url = "https://api.opencagedata.com/geocode/v1/json?key="+DISTANCE_API_KEY
    
    if request.method == "GET" and "country_from" in request.GET and "city_from" in request.GET and "county_from" in request.GET and "country_to" in request.GET and "city_to" in request.GET and "county_to" in request.GET:
        url1 = url + request.GET["county_from"] + '%2C+' + request.GET["city_from"] + '%2C+' + request.GET["country_from"] + '&pretty=1'
        url2 = url + request.GET["county_to"] + '%2C+' + request.GET["city_to"] + '%2C+' + request.GET["country_to"] + '&pretty=1'
        with urllib.request.urlopen(url1) as response1:
            string = response1.read().decode('utf-8')
            res1 = json.loads(string)
            lat1 = res1["results"][0]["geometry"]["lat"]
            lng1 = res1["results"][0]["geometry"]["lng"]
        with urllib.request.urlopen(url2) as response2:
            string = response2.read().decode('utf-8')
            res2 = json.loads(string)
            lat2 = res2["results"][0]["geometry"]["lat"]
            lng2 = res2["results"][0]["geometry"]["lng"]
    elif request.method == "POST" and "country_from" in request.POST and "city_from" in request.POST and "county_from" in request.POST and "country_to" in request.POST and "city_to" in request.POST and "county_to" in request.POST:

        url1 = url + request.POST["county_from"] + '%2C+' + request.POST["city_from"] + '%2C+' + request.POST["country_from"] + '&pretty=1'
        url2 = url + request.POST["county_to"] + '%2C+' + request.POST["city_to"] + '%2C+' + request.POST["country_to"] + '&pretty=1'
        with urllib.request.urlopen(url1) as response1:
            string = response1.read().decode('utf-8')
            res1 = json.loads(string)
            lat1 = res1["results"][0]["geometry"]["lat"]
            lng1 = res1["results"][0]["geometry"]["lng"]
        with urllib.request.urlopen(url2) as response2:
            string = response2.read().decode('utf-8')
            res2 = json.loads(string)
            lat2 = res2["results"][0]["geometry"]["lat"]
            lng2 = res2["results"][0]["geometry"]["lng"]

    rlat1 = radians(lat1)
    rlon1 = radians(lng1)
    rlat2 = radians(lat2)
    rlon2 = radians(lng2)

    dlon = rlon2 - rlon1
    dlat = rlat2 - rlat1
    a = sin(dlat / 2) ** 2 + cos(rlat1) * cos(rlat2) * sin(dlon / 2) ** 2
    c = 2 * atan2(sqrt(a), sqrt(1 - a))
    distance = 6373.0 * c


    return HttpResponse(str(distance))


def create_event(request):
    weather = None
    form = EventCreateForm()
    full_data = ''
    if request.method == 'POST':
        form = EventCreateForm(request.POST)
        if form.is_valid():
            full_data = form.cleaned_data
            weather = getWeather(full_data['city_name'])
            Event.objects.create(**full_data)
            form = EventCreateForm()

    context = {
        'weather': weather,
        'form': form,
        'data': full_data
    }
    return render(request, 'event_create.html', context)


def getWeather(city_name):
    weather = None
    url = 'http://api.openweathermap.org/data/2.5/weather?q=' + city_name + '&APPID=a37330855b4d53528b42c62007788711'
    response = ''
    try:
        response = requests.get(url)
    except requests.exceptions.RequestException as e:
        pass
    if response.ok:
        data = response.json()
        weather = data['weather'][0]['main']
    return weather

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> added ui
=======
=======

>>>>>>> quote-api is controlled, registeruser api updated with url path
def getquote(request:HttpRequest):
   
    if request.method == "GET" and "first_name" in request.GET and "last_name" in request.GET:
        url = "https://quotable.io/quotes?author=" + request.GET["first_name"] + "-" + request.GET["last_name"]
    elif request.method == "POST" and "first_name" in request.POST and "last_name" in request.POST:
        url = "https://quotable.io/quotes?author=" + request.GET["first_name"] + "-" + request.GET["last_name"]
    
    req = urllib.request.Request(url)
    with urllib.request.urlopen(req) as response:
        page = response.read().decode("utf8")
        return HttpResponse(page)
<<<<<<< HEAD
>>>>>>> Committing files of quote-api
=======
>>>>>>> Revert "new branch created"
=======

>>>>>>> quote-api is controlled, registeruser api updated with url path
