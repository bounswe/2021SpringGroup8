# User Functionalities
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

            }
        ```
