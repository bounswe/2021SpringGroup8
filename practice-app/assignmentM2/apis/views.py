from django.shortcuts import render
from django.http import HttpResponse
import requests

# Create your views here.
from .forms import EventCreateForm
from .models import Event


def event_create_view(request):

    weather = -1
    flag = True
    form = EventCreateForm()
    full_data = ''
    if request.method == 'POST':
        form = EventCreateForm(request.POST)
        if form.is_valid():
            full_data = form.cleaned_data
            url = 'http://api.openweathermap.org/data/2.5/weather?q=' + str(full_data['city_name']) + '&APPID=a37330855b4d53528b42c62007788711'
            response = ''
            try:
                response = requests.get(url)
            except requests.exceptions.RequestException as e:
                flag = False

            print(type(response), "  ", response)
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


def examplepage(request):
    return HttpResponse("<h1>API Example Page!<h1>")
