import pymongo
from bson import ObjectId

class DatabaseManager:
    
    def __init__(self) -> None:
        self.client = pymongo.MongoClient("mongodb+srv://admin:admin@cmpe451db.zec58.mongodb.net/Cmpe451DB?retryWrites=true&w=majority")
        self.db = self.client["CommunityDB"]
        self.userCollection = self.db["users"]
        self.communityCollection = self.db["communities"]
        self.usercount = 1


    # this function returns true if creating account process is valid otherwise returns false
    def signup(self, user_dict):
        username = user_dict["username"]
        myquery = { "username": username }
        mydoc = self.userCollection.find_one(myquery)
        if mydoc == None:
                x = self.userCollection.insert_one(user_dict)
                user = self.userCollection.find_one(myquery)
                user_return_dict = {"username" : username, "id": str(user["_id"]), "email": user["email"]}
                return user_return_dict
        else:
            return False


    # this function returns the id of user if the user is not real returns -1
    def signin(self, user_dict):
        user = self.userCollection.find_one(user_dict)
        if user == None :
            return False
        else:
            user_return_dict = {"username" : user["username"], "id": str(user["_id"]), "email": user["email"]}
            return user_return_dict

    def create_community(self, community_dict):
        community_title = community_dict["communityTitle"]
        myquery = {"communityTitle": community_title}
        mydoc = self.communityCollection.find_one(myquery)
        if mydoc == None:
            x = self.communityCollection.insert_one(community_dict)
            community = self.communityCollection.find_one(myquery)
            community_return_dict = {"communityTitle": community_title, "id": str(community["_id"]),
             "description": community["description"], "subscribers": community["subscribers"], "posts": community["posts"],
             "creationTime": community["creationTime"], "createdBy": community["createdBy"]}
            return community_return_dict
        else:
            return False
        
    def delete_community(self, community_id):
        id = ObjectId(community_id)
        myquerry = {"_id": id}
        mydoc = self.communityCollection.find_one(myquerry)
        if mydoc != None:
            self.communityCollection.delete_one({"_id": ObjectId(community_id)})
            return True
        else:
            return False



if __name__== "__main__":
    dbm = DatabaseManager()
    dbm.communityCollection.drop()
    x = dbm.create_community({"communityTitle": "community1", "description": "new Community here",
                            "subscribers": "", "posts": "", "creationTime": "12.11.2021", "createdBy": "123"})
#    print(dbm.signup({"username": "abaf", "password" : "12345", "email": "abca@gmail.com"}))
#    print(dbm.signup({"username": "abaf", "password" : "12345", "email": "abca@gmail.com"}))
#    print(dbm.signin({"username": "abaf", "password" : "12345"}))
    print(x)
    print(dbm.delete_community(x["id"]))
    dbm.userCollection.drop()
