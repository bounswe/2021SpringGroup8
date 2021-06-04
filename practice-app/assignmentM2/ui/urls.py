from django.urls import path
from django.urls.resolvers import URLPattern
from . import views


urlpatterns = [
    path("", views.mainpage, name="mainpage"),
    path("examplepage", views.examplepage, name="examplepage")
]