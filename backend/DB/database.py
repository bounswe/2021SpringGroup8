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

    def find_user(self, userId):
        user = self.userCollection.find_one({"_id": ObjectId(userId)})
        if user is not None:
            user_return_dict = {"username" : user["username"], "id": str(user["_id"]),
            "email": user["email"], "subscribers": user.get("subscribers"), "createdCommunities": user.get("createdCommunities")}
            user_return_dict["subscribers"] = user_return_dict["subscribers"] if user_return_dict["subscribers"] is not None else []
            user_return_dict["createdCommunities"] = user_return_dict["createdCommunities"] if user_return_dict["createdCommunities"] is not None else []
            return user_return_dict
        else:
            return False
    
    def get_user_preview(self, userId):
        user = self.userCollection.find_one({"_id": ObjectId(userId)})
        if user is not None:
            user_return_dict = {"username" : user["username"], "id": str(user["_id"])}
            return user_return_dict
        else:
            return False

    def find_subscribes_of_user(self, userId):
        user = self.userCollection.find_one({"_id": ObjectId(userId)})
        subscribes = user.get("subscribers")
        if subscribes == None:
            return False
        else:
            return subscribes

    def subscribe_community(self, userId, community_preview):
        community = self.communityCollection.find_one({"_id": ObjectId(community_preview["id"])})
        user = self.userCollection.find_one({"_id": ObjectId(userId)})
        if (community != None) and (user != None):
            self.userCollection.update_one( 
            { "_id" : user["_id"] },
            { "$push": { "subscribers": community_preview}}
            )

            self.communityCollection.update_one( 
            { "_id" : community["_id"] },
            { "$push": { "subscribers": self.get_user_preview(userId)}}
            )
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
            community_preview = {"communityTitle" : community_title, "id": str(community["_id"])}
            self.userCollection.update_one( 
            { "_id" : ObjectId(user["id"]) },
            { "$push": { "createdCommunities": community_preview}}
            )
            myquery = {"_id": ObjectId(user["id"])}
            user_object = self.userCollection.find_one(myquery)
            community_return_dict = {"communityTitle": community_title, "id": str(community["_id"]),
             "description": community["description"], "creationTime": community["creationTime"], "createdBy": user,
             "subscribers": community.get("subscribers")}
            community_return_dict["subscribers"] = community_return_dict["subscribers"] if community_return_dict["subscribers"] is not None else []

            return community_return_dict
        else:
            return False
        
    def delete_community(self, community_id):
        id = ObjectId(community_id)
        myquerry = {"_id": id}
        community = self.communityCollection.find_one(myquerry)
        if community != None:
            for user in community["subscribers"]:
                self.userCollection.update_one( 
                { "_id" : ObjectId(user) },
                { "$pull": { "subscribers": { "id": community_id} } }
                )
                self.userCollection.update_one( 
                { "_id" : ObjectId(user) },
                { "$pull": { "createdCommunities": { "id": community_id} } }
                )
                user = self.userCollection.find_one({"_id": ObjectId(user)})
            self.communityCollection.delete_one({"_id": ObjectId(community_id)})
            return True
        else:
            return False

    def get_communuties(self):
        list = []
        for community in self.communityCollection.find():
            community_dict = {"CommunityTitle": community["communityTitle"], "id": str(community["_id"]),
            "creationTime": community["creationTime"], "createdBy": community["createdBy"]}
            list.append(community_dict)
        return list

    def get_community_preview(self, communityId):
        community = self.communityCollection.find_one({"_id": ObjectId([communityId])})
        if community is not None:
            return_dict = community_dict = {"CommunityTitle": community["communityTitle"], "id": str(community["_id"]),
                "creationTime": community["creationTime"], "createdBy": community["createdBy"]}
            return return_dict
        else:
            return False

    def get_specific_community(self, communityId):
        community = self.communityCollection.find_one({"_id": ObjectId([communityId])})
        if community is not None:
            community_return_dict = {"communityTitle": community["communityTitle"], "id": str(community["_id"]),
                "description": community["description"], "creationTime": community["creationTime"], "createdBy": user,
                "subscribers": community.get("subscribers")}
            community_return_dict["subscribers"] = community_return_dict["subscribers"] if community_return_dict["subscribers"] is not None else []
            return community_return_dict
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

    def get_post_preview(self, postId):
        post = self.communityCollection.find_one({"_id": ObjectId(postId)})
        if post is not None:
            post_return_dict = {"postTitle": post["postTitle"], "id": str(post["_id"]),
            "postedBy": post["postedBy"], "creationTime": post["creationTime"]}
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
                            "creationTime": "12.11.2021"}, {"id": user["id"], "userName": user["username"]})
    community2 = dbm.create_community({"communityTitle": "community2", "description": "new Community here",
                            "creationTime": "12.11.2021"}, {"id": user["id"], "userName": user["username"]})
    community3 = dbm.create_community({"communityTitle": "community3", "description": "new Community here",
                            "creationTime": "12.11.2021"}, {"id": user["id"], "userName": user["username"]})
#    user = dbm.signup({"username": "abaf", "password" : "12345", "email": "abca@gmail.com"})
#    print(dbm.signup({"username": "abaf", "password" : "12345", "email": "abca@gmail.com"}))
#    print(dbm.signin({"username": "abaf", "password" : "12345"}))
#    print(user)
#    print(community)
    print(dbm.get_communuties())
    print(dbm.find_user(user["id"]))
    print(dbm.subscribe_community(user["id"], {"id": community["id"], "communityTitle": community["communityTitle"]}))
    
#    print(dbm.create_post({"postTitle": "post1", "description": "new post1 here",
#            "creationTime": "12.11.2021", "postedBy": "new user1", "communityId": x["id"]}))
    print(dbm.delete_community(community["id"]))
    
