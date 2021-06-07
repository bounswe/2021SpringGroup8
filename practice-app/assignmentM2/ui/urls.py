from django.urls import path
from django.urls.resolvers import URLPattern
from . import views
from apis import views as apiview

urlpatterns = [
    path("", views.mainpage, name="mainpage"),
    path("examplepage", views.examplepage, name="examplepage"),
    path("create/", apiview.event_create_view, name="eventCreate")
]
