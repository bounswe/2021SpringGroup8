from django.db import models

# Create your models here.

class Event(models.Model):
    title = models.CharField(max_length=50)
    description = models.CharField(max_length=200)
    city_name = models.CharField(max_length=20, default='Istanbul')
