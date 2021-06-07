from django.urls import path
from django.urls.resolvers import URLPattern
from . import views

urlpatterns = [
    path("examplepage", views.examplepage, name="examplepage"),
    path("getflag", views.getflag, name="getflag"),
    path("getcurrencies", views.getcurrencies, name="getcurrencies"),
    # event-create url
    path("", views.event_create_view, name="eventCreate"),
]
