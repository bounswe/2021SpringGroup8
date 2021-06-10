<<<<<<< HEAD
from django.db import models

# Create your models here.

class Event(models.Model):
    title = models.CharField(max_length=50)
    description = models.CharField(max_length=200)
    city_name = models.CharField(max_length=20, default='Istanbul')
<<<<<<< HEAD


<<<<<<< HEAD
<<<<<<< HEAD

<<<<<<< HEAD
# Create your models here.


class Meal(models.Model):
    name = models.CharField(max_length=50)
    category = models.CharField(max_length=50)
    area = models.CharField(max_length=50)
    recipe = models.TextField()
=======
>>>>>>> first commit
=======
>>>>>>> second commit
=======

# Create your models here.
>>>>>>> second draft for weather api
=======
>>>>>>> I add url path and write unittest for weather api
=======
from django.db import models

# Create your models here.


class Meal(models.Model):
    name = models.CharField(max_length=50)
    category = models.CharField(max_length=50)
    area = models.CharField(max_length=50)
    recipe = models.TextField()

class Event(models.Model):
    title = models.CharField(max_length=50)
    description = models.CharField(max_length=200)
    city_name = models.CharField(max_length=20, default='Istanbul')
<<<<<<< HEAD
<<<<<<< HEAD

<<<<<<< HEAD
>>>>>>> added ui
=======
=======

>>>>>>> Revert "new branch created"
class User(models.Model):
    name= models.CharField(max_length=30)
    username = models.CharField(max_length=30)
    email = models.CharField(max_length=250)
    isActive = models.BooleanField(default=True)
    password = models.CharField(max_length=30)
<<<<<<< HEAD
>>>>>>> model-test-view-regarding-user-registration-updated
=======
>>>>>>> Committing files of quote-api
=======
>>>>>>> Revert "new branch created"
