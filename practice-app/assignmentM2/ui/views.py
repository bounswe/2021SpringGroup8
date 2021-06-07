from django.http.response import HttpResponseRedirect
from django.shortcuts import render
from django.http import HttpResponse


# Create your views here.
def examplepage(request, *args, **kwargs):
    return render(request, "home.html", {})


def mainpage(request, *args, **kwargs):
    return HttpResponseRedirect("examplepage")
