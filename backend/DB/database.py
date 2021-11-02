import pymongo

client = pymongo.MongoClient("mongodb+srv://admin:admin@cmpe451db.zec58.mongodb.net/Cmpe451DB?retryWrites=true&w=majority")
db = client["CommunityDB"]
print(db)
print(client.list_database_names())