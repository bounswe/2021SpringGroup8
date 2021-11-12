import pymongo
from bson import ObjectId

class DatabaseManager:
    
    def __init__(self) -> None:
        self.client = pymongo.MongoClient("mongodb+srv://admin:admin@cmpe451db.zec58.mongodb.net/Cmpe451DB?retryWrites=true&w=majority")
        self.db = self.client["CommunityDB"]
        self.userCollection = self.db["users"]
        self.communityCollection = self.db["communities"]
        self.postCollection = self.db["posts"]
        self.usercount = 1


    # this function returns true if creating account process is valid otherwise returns false
    def signup(self, user_dict):
        username = user_dict["username"]
        myquery = { "username": username }
        mydoc = self.userCollection.find_one(myquery)
        if mydoc == None:
                user_dict["subscribers"] = ""
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

    def subscribe_community(self, userId, communityId):
        community = self.communityCollection.find_one({"_id": ObjectId(communityId)})
        user = self.userCollection.find_one({"_id": ObjectId(userId)})
        if (community != None) & (user != None):
            user_subscribers = user.get("subscribers")
            if user_subscribers == "":
                user_subscribers = communityId
            else:
                user_subscribers += ";" + communityId   
            newvalues = { "$set": {"subscribers": user_subscribers}}
            self.communityCollection.update_one({"_id": ObjectId(userId)}, newvalues)

            community_subscribers = community.get("subscribers")
            if community_subscribers == "":
                community_subscribers = userId
            else:
                community_subscribers += ";" + userId   
            newvalues = { "$set": {"subscribers": community_subscribers}}
            self.communityCollection.update_one({"_id": ObjectId(communityId)}, newvalues)
            return True
        else:
            return False


    def create_community(self, community_dict, user):
        community_title = community_dict["communityTitle"]
        myquery = {"communityTitle": community_title}
        mydoc = self.communityCollection.find_one(myquery)
        if mydoc == None:
            x = self.communityCollection.insert_one(community_dict)
            community = self.communityCollection.find_one(myquery)
            self.communityCollection.update_one( 
            { "_id" : community["_id"] },
            { "$set": { "createdBy": user}}
            )
            community = self.communityCollection.find_one(myquery)
            community_return_dict = {"communityTitle": community_title, "id": str(community["_id"]),
             "description": community["description"], "creationTime": community["creationTime"], "createdBy": user}
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

    def create_post(self, post_dict):
        post_title = post_dict["postTitle"]
        communityId = post_dict["communityId"]
        community = self.communityCollection.find_one({"_id": ObjectId(communityId)})
        if community != None:
            myquery = {"postTitle": post_title}
            x = self.postCollection.insert_one(post_dict)
            post = self.postCollection.find_one(myquery)
            post_return_dict = {"postTitle": post_title, "id": str(post["_id"]), "description": post["description"],
            "creationTime": post["creationTime"], "postedBy": post["postedBy"], "communityId": post["communityId"]}
            community_posts = community.get("posts")
            if community_posts == "":
                community_posts = str(post["_id"])
            else:
                community_posts += ";" + str(post["_id"])
            newvalues = { "$set": {"posts": community_posts}}
            self.communityCollection.update_one({"_id": ObjectId(communityId)}, newvalues)
            return post_return_dict
        else:
            return False



if __name__== "__main__":
    dbm = DatabaseManager()
    dbm.communityCollection.drop()
    dbm.userCollection.drop()
    dbm.postCollection.drop()
    user = dbm.signup({"username": "abaf", "password" : "12345", "email": "abca@gmail.com"})
    community = dbm.create_community({"communityTitle": "community1", "description": "new Community here",
                            "creationTime": "12.11.2021"}, {"userId": user["id"], "userName": user["username"]})
#    user = dbm.signup({"username": "abaf", "password" : "12345", "email": "abca@gmail.com"})
#    print(dbm.signup({"username": "abaf", "password" : "12345", "email": "abca@gmail.com"}))
#    print(dbm.signin({"username": "abaf", "password" : "12345"}))
#    print(user)
#    print(community)
#    print(dbm.subscribe_community(user["id"], community["id"]))
#    print(dbm.create_post({"postTitle": "post1", "description": "new post1 here",
#            "creationTime": "12.11.2021", "postedBy": "new user1", "communityId": x["id"]}))
    print(dbm.delete_community(community["id"]))
    
