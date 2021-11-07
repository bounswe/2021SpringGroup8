import pymongo

client = pymongo.MongoClient("mongodb+srv://admin:admin@cmpe451db.zec58.mongodb.net/Cmpe451DB?retryWrites=true&w=majority")
db = client["CommunityDB"]
print(db)
print(client.list_database_names())

# this function returns true if creating account process is valid otherwise returns false
def signup(password, username):
    return True

# this function returns the id of user if the user is not real returns -1
def signin(password, username):
    return -1;

# this function returns the sha256 prefix for user's password
def get_prefix():
    return 1
