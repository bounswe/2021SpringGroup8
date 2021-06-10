from django.apps import AppConfig

from pymongo import MongoClient
client = MongoClient('connection_string')
db = client['db_name']
collection=db['collection_name']
class ApisConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'apis'
