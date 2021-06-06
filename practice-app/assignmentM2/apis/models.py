from django.db import models
from django.db.models import CharField
from django.urls import reverse
import requests


class Event(models.Model):
    title = models.CharField(max_length=50)
    description = models.TextField()
    city_name = models.CharField(max_length=20,default='Istanbul')
    

    def get_absolute_url(self):
        return reverse("events:event-detail", kwargs={"id": self.id})


