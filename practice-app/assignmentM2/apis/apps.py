from django.apps import AppConfig

from pymongo import MongoClient
from decouple import config


MONGO_USERNAME=config('MONGO_USERNAME')
MONGO_PASSWORD=config('MONGO_PASSWORD')
client = MongoClient('mongodb://' + MONGO_USERNAME + ":" +MONGO_PASSWORD + '@localhost:27017/db_name')
db = client['db_name']
collection=db['collection_name']
class ApisConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'apis'
