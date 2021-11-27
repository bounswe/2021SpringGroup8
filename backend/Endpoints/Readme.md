# Objects
## Date.Object
```
    {
        "_isoformat": "2021-11-13T15:30:16.263000"
    }
```
## User.Object
```
    {
        "@type": "User.Preview",
        "id": str,
        "username": str, 
        "name": str, 
        "surname": str, 
        "city": str, 
        "birthdate": Date.Object, 
        "pplink": str, 
    }
```
## User.Preview
```
    {
        "@type": "User.Preview",
        "id": str,
        "username": str, 
        "pplink": str, 
    }
```

# User Functionalities

## View Definitions

## Update Profile
- Url: http://localhost:8080/updateprofile
- Method: POST
- Request:
    - Header:
    - Body (all fields are optional except @usertoken):
        - "@usertoken": str
        - "email" str
        - "name": str
        - "surname": str
        - "city": str
        - "birthdate": ISODATE
        - "pplink": str

- Response:
    - Header:
    - Body:
        ```   
            {
                "@context": "https://www.w3.org/ns/activitystreams",
                "@type": "User.UpdateProfile",
                "@success": "True" or "False",
                "@error": str => if @success is "False",
            }
        ```
        <details>
        <summary>Looking for an example?</summary>
        <br>
        <pre>
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "User.UpdateProfile",
            "@success": "True"
        }
        <pre>
        </details>

## Sign Up
- Url: http://localhost:8080/signup
- Method: POST
- Request:
    - Header:
    - Body:
        - "username": str
        - "email" str
        - "password": str
        - "name": str
        - "surname": str
        - "city": str
        - "birthdate": ISODATE
        - "pplink": str
- Response:
    - Header:
    - Body:
        ```   
            {
                "@context": "https://www.w3.org/ns/activitystreams",
                "@type": "User.SignUp",
                "@success": "True" or "False",
                "@return": User.Object => if @success is "True",
                "@error": str => if @success is "False",
                "@usertoken": "xaqcxukchiavwjrb",

            }
        ```
        <details>
        <summary>Looking for an example?</summary>
        <br>
        <pre>
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "User.SignUp",
            "@success": "True",
            "@return": {
                "username": "12312asdad",
                "email": "asdasd@asdasd.com",
                "name": "denemeisim",
                "surname": "denemesoyyisim",
                "birthdate": {
                    "_isoformat": "2021-11-13T15:30:16.263000"
                },
                "city": "",
                "pplink": "",
                "subscribes": [],
                "createdCommunities": [],
                "posts": [],
                "id": "61a20b7a1d423c6b62c1b172",
                "@type": "User.Object"
            },
            "@usertoken": "xaqcxukchiavwjrb"
        }
        <pre>
        </details>

## Login
- Url: http://localhost:8080/login
- Method: POST
- Request:
    - Header:
    - Body:
        - "username": str
        - "password": str
- Response:
    - Header:
    - Body:
        ```   
            {
                "@context": "https://www.w3.org/ns/activitystreams",
                "@type": "User.Login",
                "@success": "True" or "False",
                "@usertoken": str,
                "@return": User.Object if @success is "True",
                "@error": str => if @success is "False",

            }
        ```
        <details>
        <summary>Looking for an example?</summary>
        <br>
        <pre>
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "User.Login",
            "@success": "True",
            "@return": {
                "username": "sdA12323",
                "id": "618ec90b00bed43ef2daf589",
                "email": "asdasd@asda.com",
                "@type": "User.Object"
            },
            "@usertoken": "ghiitmxtelzhzjow"
        }
        <pre>
        </details>



## Get My Profile

- Request:
    - url: /getmyprofile
    - method: POST
    - Body:
        - "@usertoken": str

- Response:
    - 
    <details>
    <summary>Looking for an example?</summary>
    <br>
    <pre>
    {
        "@context": "https://www.w3.org/ns/activitystreams",
        "@type": "User.GetMyProfile",
        "@success": "True",
        "@return": {
            "username": "sdA12323",
            "id": "618faf6d9ed00de04c65ab6e",
            "email": "asdasd@asda.com",
            "subscribers": [
                {
                    "CommunityTitle": "AAAAA",
                    "id": "618fafd89ed00de04c65ab72",
                    "creationTime": {
                        "_isoformat": "2021-11-13T15:30:16.263000"
                    },
                    "createdBy": {
                    "username": "sdA12323",
                    "id": "618faf6d9ed00de04c65ab6e"
                }
            }
            ],
            "createdCommunities": [
                {
                    "communityTitle": "AAAAA",
                    "id": "618fafd89ed00de04c65ab72"
                }
            ],
            "posts": [
                {
                    "postTitle": "titledeneme",
                    "id": "618faff69ed00de04c65ab73",
                    "postedBy": {
                        "username": "sdA12323",
                        "id": "618faf6d9ed00de04c65ab6e"
                    },
                    "creationTime": {
                        "_isoformat": "2021-11-13T15:30:46.417000"
                    }
                },
                {
                    "postTitle": "titledeneme2",
                    "id": "618faff99ed00de04c65ab74",
                    "postedBy": {
                        "username": "sdA12323",
                        "id": "618faf6d9ed00de04c65ab6e"
                    },
                    "creationTime": {
                        "_isoformat": "2021-11-13T15:30:49.954000"
                    }
                }
            ],
            "@type": "User.Object"
            }
        }
    }
    <pre>



## Get User Preview

- Request:
    - url: /getuserpreview
    - method: POST
    - Body:
        - "userid": str

- Response:
    - 
    <details>
    <summary>Looking for an example?</summary>
    <br>
    <pre>
    {
        "@context": "https://www.w3.org/ns/activitystreams",
        "@type": "User.GetPreview",
        "@success": "True",
        "@return": {
            "username": "sdA12323",
            "id": "618faf6d9ed00de04c65ab6e",
            "@type": "User.Preview"
        }
    }
    <pre>





# Community Functionalities

## View Definitions
### Community.Preview
```
    {
        "CommunityTitle": str,
        "id": str,
        "creationTime": {
            "_isoformat": str
        },
        "createdBy": User.Preview,
        "@type": "Community.Preview"
    }
```


## Create Community
- Url: http://localhost:8080/createcommunity
- Method: POST
- Request:
    - Header:
    - Body:
        - "@usertoken": str
        - "communityTitle": str,
        - "description": str,

- Response:
    - Header:
    - Body:
        ```
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "Community.Create",
            "@success": "True" or "False",
            "@return": Community Object
            {
                "@type": "Community.Object",
                "id": str,
                "communityTitle": str,
                "description": str,
                "moderators": [list of User.Preview],
                "subscribers": [list of User.Preview],
                "posts": [list of Post.Preview],
                "creationTime": date time ("2021-11-12T22:57:25.918000" => ISO FORMAT),
                "createdBy": User.Preview,

            } => if @success is "True",
            "@error": str => if @success is "False",
        }
        ```
        <details>
        <summary>Looking for an example?</summary>
        <br>
        <pre>
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "Community.Create",
            "@success": "True",
            "@return": {
                "communityTitle": "sdasd213123",
                "id": "618ec91800bed43ef2daf58a",
                "description": "asdasd",
                "creationTime": "2021-11-12T23:05:44.524000",
                "createdBy": {
                    "id": "618ec90b00bed43ef2daf589",
                    "username": "sdA12323",
                    "@type": "User.Preview"
                },
                "subscribers": [],
                "@type": "Community.Object"
            }
        }
        <pre>
        </details>


## Subscribe To Community

- Request:
    - url: /subscribetocommunity
    - method: POST
    - Body:
        - "@usertoken": str
        - "communityId": str
- Response:
    - 
    <details>
    <summary>Looking for an example?</summary>
    <br>
    <pre>
    {
        "@context": "https://www.w3.org/ns/activitystreams",
        "@type": "Community.SubscribeTo",
        "@success": "True"
    }
    <pre>




## Unsubscribe Community

- Request:
    - url: /unsubscribecommunity
    - method: POST
    - Body:
        - "@usertoken": str
        - "communityId": str
- Response:
    - 
    <details>
    <summary>Looking for an example?</summary>
    <br>
    <pre>
    {
        "@context": "https://www.w3.org/ns/activitystreams",
        "@type": "Community.Unsubscribe",
        "@success": "True"
    }
    <pre>



## Get All Communities

- Request:
    - url: /getallcommunities
    - method: POST
    - Body:
- Response:
    - 
    <details>
    <summary>Looking for an example?</summary>
    <br>
    <pre>
    {
        "@context": "https://www.w3.org/ns/activitystreams",
        "@type": "Community.GetAll",
        "@success": "True",
        "@return": [
            {
                "CommunityTitle": "AAAAA",
                "id": "618fafd89ed00de04c65ab72",
                "creationTime": {
                    "_isoformat": "2021-11-13T15:30:16.263000"
                },
                "createdBy": {
                    "username": "sdA12323",
                    "id": "618faf6d9ed00de04c65ab6e"
                },
                "@type": "Community.Preview"
            }
        ]
    }
    <pre>



## Get Community

- Request:
    - url: /getcommunity
    - method: POST
    - Body:
        - "communityId": str
- Response:
    - 
    <details>
    <summary>Looking for an example?</summary>
    <br>
    <pre>
    {
        "@context": "https://www.w3.org/ns/activitystreams",
        "@type": "Community.Get",
        "@success": "True",
        "@return": {
            "communityTitle": "AAAAA",
            "id": "618fafd89ed00de04c65ab72",
            "description": "asdasd",
            "creationTime": {
                "_isoformat": "2021-11-13T15:30:16.263000"
            },
            "createdBy": {
                "username": "sdA12323",
                "id": "618faf6d9ed00de04c65ab6e"
            },
            "subscribers": [
                {
                    "username": "sdA12323",
                    "id": "618faf6d9ed00de04c65ab6e"
                }
            ],
            "posts": [
                {
                    "postTitle": "titledeneme",
                    "id": "618faff69ed00de04c65ab73",
                    "postedBy": {
                        "username": "sdA12323",
                        "id": "618faf6d9ed00de04c65ab6e"
                    },
                    "creationTime": {
                        "_isoformat": "2021-11-13T15:30:46.417000"
                    }
                },
                {
                    "postTitle": "titledeneme2",
                    "id": "618faff99ed00de04c65ab74",
                    "postedBy": {
                        "username": "sdA12323",
                        "id": "618faf6d9ed00de04c65ab6e"
                    },
                    "creationTime": {
                        "_isoformat": "2021-11-13T15:30:49.954000"
                    }
                }
            ],
            "@type": "Community.Object"
        }
    }
    <pre>








## Delete Community

- Request:
    - url: /deletecommunity
    - method: POST
    - Body:
        - "@usertoken": str
        - "communityId": str
- Response:
    - 
    <details>
    <summary>Looking for an example?</summary>
    <br>
    <pre>
    {
        "@context": "https://www.w3.org/ns/activitystreams",
        "@type": "Community.Delete",
        "@success": "True"
    }
    <pre>


# Post Functionalities

### Post.Preview
```
    {
        "@type": "Post.Preview",
        "postTitle": str,
        "id": str,
        "postedBy": User.Preview,
        "creationTime": {
            "_isoformat": str
        }
    }
```

## Submit Post

- Request:
    - url: /submitpost
    - method: POST
    - Body:
        - "@usertoken": str
        - "communityId": str
        - "title": str
        - "description": str
- Response:
    - 
    <details>
    <summary>Looking for an example?</summary>
    <br>
    <pre>
    {
        "@context": "https://www.w3.org/ns/activitystreams",
        "@type": "Post.Submit",
        "success": "True",
        "@return": {
            "postTitle": "titledeneme2",
            "id": "618faff99ed00de04c65ab74",
            "description": "descdeneme2",
            "creationTime": {
                "_isoformat": "2021-11-13T15:30:49.954000"
            },
            "postedBy": {
                "username": "sdA12323",
                "id": "618faf6d9ed00de04c65ab6e",
                "@type": "User.Preview"
            },
            "postedAt": {
                "CommunityTitle": "AAAAA",
                "id": "618fafd89ed00de04c65ab72",
                "creationTime": {
                    "_isoformat": "2021-11-13T15:30:16.263000"
                },
                "createdBy": {
                    "username": "sdA12323",
                    "id": "618faf6d9ed00de04c65ab6e",
                    "@type": "User.Preview"
                },
                "@type": "Community.Preview"
            },
            "@type": "Post.Object"
        }
    }
    <pre>






## View Post

- Request:
    - url: /viewpost
    - method: POST
    - Body:
        - "postId": str
- Response:
    - 
    <details>
    <summary>Looking for an example?</summary>
    <br>
    <pre>
    {
        "@context": "https://www.w3.org/ns/activitystreams",
        "@type": "Post.View",
        "success": "True",
        "@return": {
            "postTitle": "titledeneme2",
            "id": "618faff99ed00de04c65ab74",
            "description": "descdeneme2",
            "creationTime": {
                "_isoformat": "2021-11-13T15:30:49.954000"
            },
            "postedBy": {
                "username": "sdA12323",
                "id": "618faf6d9ed00de04c65ab6e"
            },
            "postedAt": {
                "CommunityTitle": "AAAAA",
                "id": "618fafd89ed00de04c65ab72",
                "creationTime": {
                    "_isoformat": "2021-11-13T15:30:16.263000"
                },
                "createdBy": {
                    "username": "sdA12323",
                    "id": "618faf6d9ed00de04c65ab6e"
                }
            },
            "@type": "Post.Object"
        }
    }
    <pre>



## Delete Post

- Request:
    - url: /deletepost
    - method: POST
    - Body:
        - "@usertoken": str
        - "postId": str
- Response:
    - 
    <details>
    <summary>Looking for an example?</summary>
    <br>
    <pre>
    {
        "@context": "https://www.w3.org/ns/activitystreams",
        "@type": "Post.Delete",
        "success": "True"
    }
    <pre>