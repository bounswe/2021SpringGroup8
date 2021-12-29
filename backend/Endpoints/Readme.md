# Objects Definitions
## Filter.Object
Attention! This object is an array, not a dictionary or map.
``` json
[
    filtername: str,
    fieldname: str,
    params: [param1, param2 ....]
]
```
### Filter Types
There are 5 filters whose filtername's are "search text", "greater", "less", "greater or equal", "less or equal", "equal", "checked", "unchecked".

    "search text": Searches for a text provided in params, in a given field. Requires 1 param.
            
    "greater": if a field value is greater than the parameter given. Requires 1 param.

    "less": if a field value is less than the parameter given. Requires 1 param.

    "greater or equal": if a field value is greater than or equal to the parameter given. Requires 1 param.

    "less or equal": if a field value is less than or equal to the parameter given. Requires 1 param.

    "equal": if a field value is equal to the parameter given. Requires 1 param.

    "checked": if a field value is bool and checked. Requires 0 param.
            
    "unchecked": if a field value is bool and unchecked. Requires 0 param.
            



## Date.Object
``` json
{
    "_isoformat": "2021-11-13T15:30:16.263000"
}
```
## Location.Object
``` json
{
    "@type": "Location.Object",
    "locname": str,
    "longitude": double,
    "latitude": double
}
```
## DataType.Object
``` json
{
    "@type": "DataType.Object",
    "name": str,
        "fields": {
        "fieldname1": "str",
        "fieldname2": "int",
        "fieldname3": "bool",
        "fieldname4": "location",
        //"fieldname5": "image",
        "fieldname6": "datetime"
    }
}
```
## User.Object
``` json
{
    "@type": "User.Preview",
    "id": str,
    "username": str, 
    "name": str, 
    "surname": str, 
    "loc": Location.Object, 
    "birthdate": Date.Object, 
    "pplink": str, 
}
```
## Community.Object
``` json
{
    "@type": "Community.Object",
    "id": str,
    "communityTitle": str,
    "description": str,
    //"moderators": [list of User.Preview],
    "dataTypes": [list of DataType.Object],
    "subscribers": [list of User.Preview],
    "posts": [list of Post.Objects],
    "creationTime": Date.Object,
    "createdBy": User.Preview,
}
```
## Post.Object
``` json
{
    "postTitle": str,
    "dataTypeName": str,
    "fieldValues": {  (this is an example field values, it depends on the data type)
        "a": "asdasd",
        "b": 412,
        "c": {
            "locname": "Istanbul",
            "longitude": 1.5,
            "latitude": 1.7,
            "@type": "Location.Object"
        }
    },
    "creationTime": Date.Object,
    "postedAt": Community.Preview,
    "postedBy": User.Preview,
    "id": str,
    "@type": "Post.Object"
}
```
# View Definitions
## User.Preview
``` json
{
    "@type": "User.Preview",
    "id": str,
    "username": str, 
    "pplink": str, 
}
```

## Community.Preview
``` json
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

## Post.Preview
``` json
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


# User Functionalities


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
        - "loc": Location.Object
        - "birthdate": ISODATE
        - "pplink": str

- Response:
    - Header:
    - Body:
        ``` json  
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "User.UpdateProfile",
            "@success": "True" or "False",
            "@error": str => if @success is "False",
        }
        ```

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
        - "loc": Location.Object (optional)
        - "birthdate": ISODATE (optional)
        - "pplink": str (optional)
- Response:
    - Header:
    - Body:
        ``` json   
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "User.SignUp",
            "@success": "True" or "False",
            "@return": User.Object => if @success is "True",
            "@error": str => if @success is "False",
            "@usertoken": str,
        }
        ```

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
        ``` json   
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "User.Login",
            "@success": "True" or "False",
            "@usertoken": str,
            "@return": User.Object if @success is "True",
            "@error": str => if @success is "False",

        }
        ```


## Get My Profile
- Url: http://127.0.0.1:8080/getmyprofile
- Method: POST
- Request:
    - Header:
    - Body:
        - "@usertoken": str

- Response:
    - Header:
    - Body:
        ``` json       
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "User.GetMyProfile",
            "@success": "True",
            "@return": User.Object
        }
        ```   


## Get User Preview
- Url: http://127.0.0.1:8080/getuserpreview
- Method: POST
- Request:
    - Header:
    - Body:
        - "userid": str

- Response:
    - Header:
    - Body:
        ``` json       
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "User.GetPreview",
            "@success": "True",
            "@return": User.Preview
        }
        ```  

# Community Functionalities


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
        ``` json
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "Community.Create",
            "@success": "True" or "False",
            "@return": Community Object => if @success is "True",
            "@error": str => if @success is "False",
        }
        ```

## Subscribe To Community
- Url: http://localhost:8080/subscribetocommunity
- Method: POST
- Request:
    - Header:
    - Body:
        - "@usertoken": str
        - "communityId": str
- Response:
    - Header:
    - Body:
        ``` json
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "Community.SubscribeTo",
            "@success": "True" or "False"
        }
        ```


## Unsubscribe Community
- Url: http://localhost:8080/unsubscribecommunity
- Method: POST
- Request:
    - Header:
    - Body:
        - "@usertoken": str
        - "communityId": str
- Response:
    - Header:
    - Body:
        ``` json
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "Community.Unsubscribe",
            "@success": "True" or "False"
        }
        ```

## Get All Communities
- Url: http://localhost:8080/getallcommunities
- Method: POST
- Request:
    - Header:
    - Body:
- Response:
    - Header:
    - Body:
        ``` json
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "Community.GetAll",
            "@success": "True",
            "@return": [list of Community.Preview]
        }
        ```



## Get Community
- Url: http://localhost:8080/getcommunity
- Method: POST
- Request:
    - Header:
    - Body:
        - "communityId": str
- Response:
    - Header:
    - Body:
        ``` json
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "Community.Get",
            "@success": "True",
            "@return": Community.Object
        }
        ```





## Delete Community
- Url: http://localhost:8080/deletecommunity
- Method: POST
- Request:
    - Header:
    - Body:
        - "@usertoken": str
        - "communityId": str
- Response:
    - Header:
    - Body:
        ``` json
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "Community.Delete",
            "@success": "True" or "False"
        }
        ```

## Create Data Type
- Url: http://localhost:8080/createdatatype
- Method: POST
- Request:
    - Header:
    - Body:
        - "@usertoken": str
        - "communityId": str
        - "datatypename": str
        - "datatypefields": Json | example `{"a":"str","b":"int","c":"location"}`,

- Response:
    - Header:
    - Body:
        ``` json
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "Community.CreateDataType",
            "@success": "True" or "False"
        }
        ```

## Search Community
- Url: http://localhost:8080/searchcommunity
- Method: POST
- Request:
    - Header:
    - Body:
        - "searchtext": str
- Response:
    - Header:
    - Body:
        ``` json
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "Community.Search",
            "@success": "True" or "False",
            "@return": [Array of Community.Preview]
        }
        ```


# Post Functionalities

## Submit Post
- Url: http://localhost:8080/submitpost
- Method: POST
- Request:
    - Header:
    - Body:
        - "@usertoken": str
        - "communityId": str
        - "title": str
        - "datatypename": str
        - "datatypevalues": Json | example => `{"a":"asdasd","b":412,"c":{ "locname": "Istanbul", "longitude": 1.5, "latitude": 1.7}}`

- Response:
    - Header:
    - Body:
        ``` json
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "Post.Submit",
            "success": "True",
            "@return": Post.Object
        }
        ```






## View Post
- Url: http://localhost:8080/viewpost
- Method: POST
- Request:
    - Header:
    - Body:
        - "postId": str
- Response:
    - Header:
    - Body:
        ``` json
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "Post.View",
            "success": "True",
            "@return": Post.Object
        }
        ```



## Delete Post
- Url: http://localhost:8080/deletepost
- Method: POST
- Request:
    - Header:
    - Body:
        - "@usertoken": str
        - "postId": str
- Response:
    - Header:
    - Body:
        ``` json
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "Post.Delete",
            "success": "True" or "False"
        }
        ```

## Search Post
- Url: http://localhost:8080/searchpost
- Method: POST
- Request:
    - Header:
    - Body:
        - "communityId": str
        
        - "datatypename": str
        - "filters": [Array of Filter.Object] 
            - example input: 
                - "communityId": "61b79b8b596357c065841556"
                - "datatypename": ChampionsLeague
                - "filters": [["search text","Between", ["f"]]]
- Response:
    - Header:
    - Body:
        ``` json
        {
            "@context": "https://www.w3.org/ns/activitystreams",
            "@type": "Post.Search",
            "success": "True",
            "@return": [Array of Post.Object]
        }
        ```