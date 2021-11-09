import pymongo


class DatabaseManager:
    
    def __init__(self) -> None:
        self.client = pymongo.MongoClient("mongodb+srv://admin:admin@cmpe451db.zec58.mongodb.net/Cmpe451DB?retryWrites=true&w=majority")
        self.db = self.client["CommunityDB"]
        self.userCollection = self.db["users"]
        self.usercount = 1


    # this function returns true if creating account process is valid otherwise returns false
    def signup(self, user_dict):
        username = user_dict["username"]
        myquery = { "username": username }
        mydoc = self.userCollection.find_one(myquery)
        if mydoc == None:
                x = self.userCollection.insert_one(user_dict)
                user = self.userCollection.find_one(myquery)
                user_return_dict = {"username" : username, "id": user["_id"], "email": user["email"]}
                return user_return_dict
        else:
            return False


    # this function returns the id of user if the user is not real returns -1
    def signin(self, user_dict):
        user = self.userCollection.find_one(user_dict)
        if user == None :
            return False
        else:
            user_return_dict = {"username" : user["username"], "id": user["_id"], "email": user["email"]}
            return user_return_dict

if __name__== "__main__":
    dbm = DatabaseManager()
    print(dbm.signup({"username": "abaf", "password" : "12345", "email": "abca@gmail.com"}))
    print(dbm.signup({"username": "abaf", "password" : "12345", "email": "abca@gmail.com"}))
    print(dbm.signin({"username": "abaf", "password" : "12345"}))
    dbm.userCollection.drop()
