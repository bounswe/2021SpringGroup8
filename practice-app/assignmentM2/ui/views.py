from django.http.response import HttpResponseRedirect
from django.shortcuts import render
from django.http import HttpResponse
from .forms import *

# Create your views here.


def mainpage(response):
    if response.method == 'POST':
        
        form = FlagForm(response.POST)
        
        if form.is_valid():
            
            new_url = '/apis/getflag?country=' + form.cleaned_data['country_name']
            return HttpResponseRedirect(new_url)
    else:
        form = FlagForm()
    return render(response, "home.html", {'form': form})



def economy(request):
    if request.method == 'POST':
        
        form = CurrencyForm(request.POST)
        
        if form.is_valid():
            
            new_url = '/apis/getcurrencies?from=' + form.cleaned_data['cur_from'] + '&to=' + form.cleaned_data['cur_to']
            return HttpResponseRedirect(new_url)
    else:
        form = CurrencyForm()

    return render(request, 'economy.html', {'form': form})

def distance(request):
    if request.method == 'POST':
        
        form = DistanceForm(request.POST)
        
        if form.is_valid():
            
            new_url = '/apis/finddistance?country_from=' + form.cleaned_data['country_from'] + '&city_from=' + form.cleaned_data['city_from'] + '&county_from=' + form.cleaned_data['county_from'] + '&country_to=' + form.cleaned_data['country_to'] + '&city_to=' + form.cleaned_data['city_to'] + '&county_to=' + form.cleaned_data['county_to']
            return HttpResponseRedirect(new_url)
    else:
        form = DistanceForm()

    return render(request, 'distance.html', {'form': form})

def cooking(request):
    if request.method == 'POST':
        
        form = MealForm(request.POST)
        
        if form.is_valid():
            
            new_url = '/apis/getmealrecipebyname?s=' + form.cleaned_data['meal_name']
            return HttpResponseRedirect(new_url)
    else:
        form = MealForm()

    return render(request, 'cooking.html', {'form': form})