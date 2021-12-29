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
                user_return_dict = self.find_user(str(user["_id"]))
                return user_return_dict
        else:
            return False


    # this function returns the id of user if the user is not real returns -1
    def signin(self, user_dict):
        user = self.userCollection.find_one(user_dict)
        if user == None :
            return False
        else:
            user_return_dict = self.find_user(str(user["_id"]))
            return user_return_dict

    def update_profile(self, user_id, user_dict):
        user = self.userCollection.find_one({"_id": ObjectId(user_id)})
        if user == None:
            return False
        else:
            for feature in user_dict:
                self.userCollection.update_one( 
                { "_id" : user["_id"] },
                { "$set": {feature: user_dict[feature]}}
                )
        return True

    def find_user(self, userId):
        user = self.userCollection.find_one({"_id": ObjectId(userId)})
        if user is not None:
            user_id = str(user["_id"])
            user.pop("_id")
            user["id"] = user_id
            user.pop("password")
            return user
        else:
            return False
    
    def get_user_preview(self, userId):
        user = self.userCollection.find_one({"_id": ObjectId(userId)})
        if user is not None:
            user_return_dict = {"username" : user["username"], "id": str(user["_id"]), "pplink": user["pplink"]}
            return user_return_dict
        else:
            return False

    def is_subscribed(self, user_id, community_id):
        user = self.userCollection.find_one({"_id": ObjectId(user_id)})
        subscribes = user.get("subscribes")
        for subscribe in subscribes:
            if subscribe["id"] == community_id:
                return True
        return False

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
        community_dict["dataTypes"] = []
        if mydoc == None:
            x = self.communityCollection.insert_one(community_dict)
            community = self.communityCollection.find_one(myquery)
            self.communityCollection.update_one( 
            { "_id" : community["_id"] },
            { "$set": { "createdBy": user}}
            )
            community = self.communityCollection.find_one(myquery)
            community_preview = self.get_community_preview(str(community["_id"]))
            self.userCollection.update_one( 
            { "_id" : ObjectId(user["id"]) },
            { "$push": { "createdCommunities": community_preview}}
            )
            myquery = {"_id": ObjectId(user["id"])}
            user_object = self.userCollection.find_one(myquery)
            community_return_dict = self.get_specific_community(str(community["_id"]))
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
                
            user = community["createdBy"]
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
            community_dict = self.get_community_preview(str(community["_id"]))
            list.append(community_dict)
        return list

    def get_community_preview(self, communityId):
        community = self.communityCollection.find_one({"_id": ObjectId(communityId)})
        if community is not None:
            return_dict = community_dict = {"CommunityTitle": community["communityTitle"], "id": str(community["_id"]),
                "creationTime": community["creationTime"], "createdBy": community["createdBy"], "description":community["description"]}
            return return_dict
        else:
            return False

    def create_dataType(self, name, dataTypeFields, communityPreview):
        communityId = communityPreview["id"]
        datatype = {
        "name": name,
        "fields": dataTypeFields
    }
        community = self.get_specific_community(communityId)
        for DT in community["dataTypes"]:
            if DT["name"] == name:
                return False
        self.communityCollection.update_one( 
        { "_id" : ObjectId(communityId)},
        { "$push": { "dataTypes": datatype}}
        )
        return True

    def find_dataType(self, dataTypeName, communityPreview):
        communityId = communityPreview["id"]
        community = self.communityCollection.find_one({"_id": ObjectId(communityId)})
        for dataType in community["dataTypes"]:
            if dataType["name"] == dataTypeName:
                return dataType
        return False

    def get_specific_community(self, communityId):
        community = self.communityCollection.find_one({"_id": ObjectId(communityId)})
        if community is not None:
            community.pop("_id")
            community["id"] = communityId
            return community
        else:
            return False

    def create_post(self, post_dict, user_preview, community_preview):
        post_title = post_dict["postTitle"]
        post_dict["postedAt"] = community_preview
        post_dict["postedBy"] = user_preview
        x = self.postCollection.insert_one(post_dict)
        post = self.postCollection.find_one({"_id": x.inserted_id})
        self.communityCollection.update_one( 
        { "_id" : ObjectId(community_preview["id"])},
        { "$push": { "posts": self.get_specific_post(str(post["_id"]))}}
        )
        self.userCollection.update_one( 
        { "_id" : ObjectId(user_preview["id"])},
        { "$push": { "posts": self.get_post_preview(str(post["_id"]))}}
        )
        post_return_dict = self.get_specific_post(str(post["_id"]))
        return post_return_dict

    def delete_post(self, postId):
        post = self.postCollection.find_one({"_id": ObjectId(postId)})
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

    def get_community_posts(self, community_preview):
        list = []
        community = self.communityCollection.find_one({"_id": ObjectId(community_preview["id"])})
        for post in community["posts"]:
            post_return_dict = {"postTitle": post["postTitle"], "id": str(post["id"]),
            "postedBy": post["postedBy"], "creationTime": post["creationTime"]}
            list.append(post_return_dict)
        return list

    def get_specific_post(self, postId):
        post = self.postCollection.find_one({"_id": ObjectId(postId)})
        if post is not None:
            post.pop("_id")
            post["id"] = postId
            return post
        else:
            return False

    def get_post_preview(self, postId):
        post = self.postCollection.find_one({"_id": ObjectId(postId)})
        if post is not None:
            post_return_dict = {"postTitle": post["postTitle"], "id": str(post["_id"]),
            "postedBy": post["postedBy"], "creationTime": post["creationTime"]}
            return post_return_dict
        else:
            return False

    def community_name_search(self, name):
        regex = name
        communityList = list(self.communityCollection.find({"$or": [{ "communityTitle": { '$regex': regex,'$options':'i'} },{ "description": { '$regex': regex, '$options':'i'}}]}))
        communityReturnList = []
        for community in communityList:
            community_dict = self.get_community_preview(str(community["_id"]))
            communityReturnList.append(community_dict)
        return communityReturnList

    def post_search(self, communityId, dataTypeName, filters):
        post_list = []
        community = self.get_specific_community(communityId)
        if community == False:
            return False

        for post in community["posts"]:
            if post["dataTypeName"] == dataTypeName:
                valid = True
                for filter in filters:
                    filterName = filter[0]
                    fieldName = filter[1]
                    params = filter[2]
                    if filterName == "search text":
                        valid = valid and self.search_text(post["fieldValues"][fieldName], params)
                        if valid == False : break
                if valid : post_list.append(post)#self.get_post_preview(post["id"]))   
        
        return post_list

    def search_text(self, fieldValue, params):
        return params[0].lower() in fieldValue.lower()
                        
                
                



if __name__== "__main__":
    dbm = DatabaseManager()
#    print(dbm.get_communuties())
    print(dbm.community_name_search("DD"))
#    print(dbm.update_profile("619cdff3bb35199a704b7c9d", {"pplink":"test-pplink"}))
#    print(dbm.subscribe_community("619cdff3bb35199a704b7c9d", dbm.get_community_preview("619ce04502e2845ef0c47701")))
#    print(dbm.is_subscribed("619cdff3bb35199a704b7c9d", "619ce04502e2845ef0c47700"))
#    x = dbm.find_user("61aea3219715d896eb60d145")   
#    print(x)
#    print(dbm.find_dataType( "NewDataType", dbm.get_community_preview("61b489c3c52c05465a0ced06")))
#    print(dbm.get_communuties())
#    change = {}
#    change["email"] = "backend3@gmail.com"
#    change["name"] = "test-name"
#    print(dbm.update_profile(x["id"], change))
#    print(dbm.find_user("619cdff3bb35199a704b7c9d"))
#    print(dbm.create_post({"postTitle": "backend_post3", "description": "post3", "creationTime": "23.11.2021"},
#     dbm.get_user_preview("619cdff3bb35199a704b7c9d"), dbm.get_community_preview("619ce04502e2845ef0c47701")))

#    print(dbm.get_communuties())
#    print(dbm.signup({"username": "backend_test_new", "password" : "backend", "email": "backend@gmail.com"}))
#    print(dbm.create_community({"communityTitle": "backend_test_community", "description": "new Community here",
#                            "creationTime": "23.11.2021"}, {"id": "619cdff3bb35199a704b7c9d", "userName": "backend_test_new"}))

#    print(dbm.get_specific_community("619ce04502e2845ef0c47701"))
#    print(dbm.find_user("619cdff3bb35199a704b7c9d"))

#    print(dbm.get_community_posts(dbm.get_community_preview("619ce04502e2845ef0c47701")))
    
#    print(dbm.delete_community("618f8c8b81bbfc3fd4a308ad"))
#    dbm.communityCollection.drop()
#    dbm.userCollection.drop()
#    dbm.postCollection.drop()
#    print(dbm.delete_community("618fad8e942b76747436aaa6"))
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
    
