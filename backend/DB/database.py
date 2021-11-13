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
        user_dict["subscribes"] = []
        user_dict["createdCommunities"] = []
        user_dict["posts"] = []
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
            "email": user["email"], "subscribers": user.get("subscribes"), "createdCommunities": user.get("createdCommunities"), 
            "posts": user["posts"]}
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
        subscribes = user.get("subscribes")
        return subscribes

    def subscribe_community(self, userId, community_preview):
        community = self.communityCollection.find_one({"_id": ObjectId(community_preview["id"])})
        user = self.userCollection.find_one({"_id": ObjectId(userId)})
        if (community != None) and (user != None):
            self.userCollection.update_one( 
            { "_id" : user["_id"] },
            { "$push": { "subscribes": community_preview}}
            )

            self.communityCollection.update_one( 
            { "_id" : community["_id"] },
            { "$push": { "subscribers": self.get_user_preview(userId)}}
            )
            return True
        else:
            return False

    def unsubscribe_community(self, userId, community_preview):
        user = self.userCollection.find_one({"_id": ObjectId(userId)})
        if user is not None:
            self.userCollection.update_one( 
            { "_id" : ObjectId(userId) },
            { "$pull": { "subscribes": community_preview}}
            ) 
            
            self.communityCollection.update_one( 
            { "_id" : ObjectId(community_preview["id"]) },
            { "$pull": { "subscribers": self.get_user_preview(userId)}}
            )
        else:
            return False

    def create_community(self, community_dict, user):
        community_title = community_dict["communityTitle"]
        myquery = {"communityTitle": community_title}
        mydoc = self.communityCollection.find_one(myquery)
        community_dict["posts"] = []
        community_dict["subscribers"] = []
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
             "subscribers": community.get("subscribers"), "posts": community.get("posts")}
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
                { "_id" : ObjectId(user["id"]) },
                { "$pull": { "subscribes": { "id": community_id} } }
                )
                self.userCollection.update_one( 
                { "_id" : ObjectId(user["id"]) },
                { "$pull": { "createdCommunities": { "id": community_id} } }
                )
            for post in community["posts"]:
                self.delete_post(post["id"])
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
        community = self.communityCollection.find_one({"_id": ObjectId(communityId)})
        if community is not None:
            return_dict = community_dict = {"CommunityTitle": community["communityTitle"], "id": str(community["_id"]),
                "creationTime": community["creationTime"], "createdBy": community["createdBy"]}
            return return_dict
        else:
            return False

    def get_specific_community(self, communityId):
        community = self.communityCollection.find_one({"_id": ObjectId(communityId)})
        if community is not None:
            community_return_dict = {"communityTitle": community["communityTitle"], "id": str(community["_id"]),
                "description": community["description"], "creationTime": community["creationTime"], "createdBy": community["createdBy"],
                "subscribers": community.get("subscribers"), "posts": community.get("posts")}
            return community_return_dict
        else:
            return False

    def create_post(self, post_dict, user_preview, community_preview):
        post_title = post_dict["postTitle"]
        community = self.communityCollection.find_one({"_id": ObjectId(community_preview["id"])})
        post_dict["postedAt"] = community_preview
        post_dict["postedBy"] = user_preview
        if community != None:
            myquery = {"postTitle": post_title}
            x = self.postCollection.insert_one(post_dict)
            post = self.postCollection.find_one(myquery)
            post_return_dict = {"postTitle": post_title, "id": str(post["_id"]), "description": post["description"],
            "creationTime": post["creationTime"], "postedBy": post["postedBy"], "postedAt": post["postedAt"]}
            self.communityCollection.update_one( 
            { "_id" : community["_id"] },
            { "$push": { "posts": self.get_post_preview(str(post["_id"]))}}
            )
            return post_return_dict
        else:
            return False

    def delete_post(self, postId):
        post = self.communityCollection.find_one({"_id": ObjectId(postId)})
        if post is not None:
            user = post["postedBy"]
            self.userCollection.update_one(
            { "_id" : ObjectId(user["id"]) },
            { "$pull": { "posts": self.get_post_preview(str(post["_id"]))}}
            )
            community = post["postedAt"]
            self.communityCollection.update_one(
            { "_id" : ObjectId(community["id"]) },
            { "$pull": { "posts": self.get_post_preview(str(post["_id"]))}}    
            )
            self.postCollection.delete_one({"_id": ObjectId(postId)})
        else:
            return False

    def get_posts(self):
        list = []
        for post in self.postCollection.find():
            post_return_dict = {"postTitle": post["postTitle"], "id": str(post["_id"]),
            "postedBy": post["postedBy"], "creationTime": post["creationTime"]}
            list.append(post_return_dict)
        return list

    def get_specific_post(self, postId):
        post = self.communityCollection.find_one({"_id": ObjectId(postId)})
        if post is not None:
            post_return_dict = {"postTitle": post["postTitle"], "id": str(post["_id"]), "description": post["description"],
            "creationTime": post["creationTime"], "postedBy": post["postedBy"], "postedAt": post["postedAt"]}
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
#   print(dbm.find_user("618f8fa7882b1ed439c85864"))
#    print(dbm.get_communuties())
    print(dbm.create_community({"communityTitle": "community4", "description": "new Community here",
                            "creationTime": "12.11.2021"}, {"id": "618ed5b261997b98afbac9b3", "userName": "sdA12323"}))
    
#    print(dbm.delete_community("618f8c8b81bbfc3fd4a308ad"))
#    dbm.communityCollection.drop()
#    dbm.userCollection.drop()
#    dbm.postCollection.drop()
#    user = dbm.signup({"username": "abaf", "password" : "12345", "email": "abca@gmail.com"})
#    community = dbm.create_community({"communityTitle": "community1", "description": "new Community here",
#                            "creationTime": "12.11.2021"}, {"id": user["id"], "userName": user["username"]})
#    community2 = dbm.create_community({"communityTitle": "community2", "description": "new Community here",
#                            "creationTime": "12.11.2021"}, {"id": user["id"], "userName": user["username"]})
#    community3 = dbm.create_community({"communityTitle": "community3", "description": "new Community here",
#                            "creationTime": "12.11.2021"}, {"id": user["id"], "userName": user["username"]})
#    user = dbm.signup({"username": "abaf", "password" : "12345", "email": "abca@gmail.com"})
#    print(dbm.signup({"username": "abaf", "password" : "12345", "email": "abca@gmail.com"}))
#    print(dbm.signin({"username": "abaf", "password" : "12345"}))
#    print(user)
#    print(community)
#    print(dbm.get_communuties())
#    print(dbm.find_user(user["id"]))
#    print(dbm.subscribe_community(user["id"], {"id": community["id"], "communityTitle": community["communityTitle"]}))
    
#    print(dbm.create_post({"postTitle": "post1", "description": "new post1 here",
#            "creationTime": "12.11.2021", "postedBy": "new user1", "communityId": x["id"]}))
#    print(dbm.delete_community(community["id"]))
    
