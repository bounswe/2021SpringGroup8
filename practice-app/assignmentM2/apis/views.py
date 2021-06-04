from django.shortcuts import render
from django.http import HttpResponse

# Create your views here.


def examplepage(request):
    return HttpResponse("<h1>API Example Page!<h1>")


