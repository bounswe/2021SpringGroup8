from django.urls import path
from django.urls.resolvers import URLPattern
from . import views


urlpatterns = [
    path("examplepage", views.examplepage, name="examplepage"),
    path("getflag", views.getflag, name="getflag"),
    path("getcurrencies", views.getcurrencies, name="getcurrencies"),
    path("getrandommealrecipe", views.getrandommealrecipe, name="getrandommealrecipe"),
    path("getmealrecipebyname", views.getmealrecipebyname, name="getmealrecipebyname"),
]