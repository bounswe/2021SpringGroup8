import pymongo

client = pymongo.MongoClient("mongodb+srv://admin:admin@cmpe451db.zec58.mongodb.net/Cmpe451DB?retryWrites=true&w=majority")
db = client["CommunityDB"]
userCollection = db["users"]
usercount = 1


# this function returns true if creating account process is valid otherwise returns false
def signup(user_dict):
    username = user_dict["username"]
    myquery = { "username": username }
    mydoc = userCollection.find_one(myquery)
    if mydoc == None:
            x = userCollection.insert_one(user_dict)
            user = userCollection.find_one(myquery)
            user_return_dict = {"username" : username, "id": user["_id"], "email": user["email"]}
            return user_return_dict
    else:
        return False


# this function returns the id of user if the user is not real returns -1
def signin(user_dict):
    user = userCollection.find_one(user_dict)
    if user == None :
        return False
    else:
        user_return_dict = {"username" : user["username"], "id": user["_id"], "email": user["email"]}
        return user_return_dict

if __name__== "__main__":
    print(signup({"username": "abaf", "password" : "12345", "email": "abca@gmail.com"}))
    print(signin({"username": "abaf", "password" : "12345"}))
    userCollection.drop()
