from typing import List
from django.http.request import HttpRequest
from django.shortcuts import render
from django.http import HttpResponse

# Event model
from .forms import EventCreateForm
from .models import Event
import requests

# Create your views here.
import urllib


def examplepage(request):
    return HttpResponse("<h1>API Example Page!<h1>")


def getflag(request: HttpRequest):
    if request.method == "GET" and "country" in request.GET:
        return HttpResponse("<img src=\"https://www.countryflags.io/" + request.GET["country"] + "/flat/64.png\">")
    elif request.method == "POST" and "country" in request.POST:
        return HttpResponse("<img src=\"https://www.countryflags.io/" + request.POST["country"] + "/flat/64.png\">")

    return HttpResponse("""<img src="https://www.countryflags.io/be/flat/64.png">""")


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
        num = (page[page.find(":") + 1:-1])
        return HttpResponse(num)


def event_create_view(request):
    weather = -1
    flag = True
    form = EventCreateForm()
    full_data = ''
    if request.method == 'POST':
        form = EventCreateForm(request.POST)
        if form.is_valid():
            full_data = form.cleaned_data
            url = 'http://api.openweathermap.org/data/2.5/weather?q=' + str(
                full_data['city_name']) + '&APPID=a37330855b4d53528b42c62007788711'
            response = ''
            try:
                response = requests.get(url)
            except requests.exceptions.RequestException as e:
                flag = False

            if response.ok:
                data = response.json()
                weather = data['weather'][0]['main']

            Event.objects.create(**full_data)
            form = EventCreateForm()

    context = {
        'weather': weather,
        'form': form,
        'data': full_data
    }
    return render(request, 'event_create.html', context)


