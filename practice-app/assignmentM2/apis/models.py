from django.db import models
from django.urls import reverse


class Event(models.Model):
    title = models.CharField(max_length=50)
    description = models.CharField(max_length=200)
    city_name = models.CharField(max_length=20, default='Istanbul')

    def get_absolute_url(self):
        return reverse("events:event-detail", kwargs={"id": self.id})

