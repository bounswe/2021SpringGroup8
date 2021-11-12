# User Functionalities

## View Definitions
### User.Preview
```
    {
        "@type": "User.Preview",
        "id": str,
        "username": str, 
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
- Response:
    - Header:
    - Body:
        ```   
            {
                "@context": "https://www.w3.org/ns/activitystreams",
                "@type": "User.SignUp",
                "@success": "True" or "False",
                "@return": 
                {
                    "@type": "User.Object",
                    "id": str,
                    "username": str,
                    "email": str,
                } => if @success is "True",
                "@error": str => if @success is "False",

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
        ```   
            {
                "@context": "https://www.w3.org/ns/activitystreams",
                "@type": "User.Login",
                "@success": "True" or "False",
                "@usertoken": str,
                "@return": 
                {
                    "@type": "User.Object",
                    "id": str,
                    "username": str,
                    "email": str,
                } => if @success is "True",
                "@error": str => if @success is "False",

            }
        ```

# Community Functionalities

## View Definitions
### Post.Preview
```
    {
        "@type": "Post.Preview",
        "id": str,
        "title": str, 
        "postedy": str,
        "createtime": date time,
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
        - "communityDescription": str,

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
                "title": str,
                "description": str,
                "moderators": [list of User.Preview],
                "subscribers": [list of User.Preview],
                "posts": [list of Post.Preview],
                "createtime": date time,
                "createdby": User.Preview,

            } => if @success is "True",
            "@error": str => if @success is "False",
        }
        ```
        