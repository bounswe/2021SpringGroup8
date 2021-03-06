from django.db import models

# Create your models here.


class Meal(models.Model):
    name = models.CharField(max_length=50)
    category = models.CharField(max_length=50)
    area = models.CharField(max_length=50)
    recipe = models.TextField()

class Event3(models.Model):
    title = models.CharField(max_length=50)
    description = models.CharField(max_length=200)
    city_name = models.CharField(max_length=20, default='Istanbul')

class User(models.Model):
    name= models.CharField(max_length=30)
    username = models.CharField(max_length=30)
    email = models.CharField(max_length=250)
    isActive = models.BooleanField(default=True)
    password = models.CharField(max_length=30)
