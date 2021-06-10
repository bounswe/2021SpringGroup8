from django.urls import path
from django.urls.resolvers import URLPattern
from . import views


urlpatterns = [
    path("", views.mainpage, name="mainpage"),
    path("economy", views.economy, name="economy"),
    path("literature", views.literature, name="literature"),
    path("cooking", views.cooking, name="cooking"),
    path("distance", views.distance, name="distance"),
    path("registeruser", views.registeruser, name="registeruser")
]