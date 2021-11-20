# Requirements
Required libraries can be found in /requirements.txt !

Run:
```
pip3 install -r requirements.txt
python3 server.py
```

## Server
Implemented with python internal http request handlers! No need for specific installation. Runs at http port 8080.

## Database
Used mongoDB framework! Database is online and there is no need for specific installation.

## Folder Hierarchy
  - README.md -> Documentation for backend
  - requirements.txt -> Pip libraries required
  - server.py -> Main server code
  - ProcessRequests.py -> Processing requests here
    - DB
      - database.py -> Database functionalities implemented
    - Endpoints
      - User.py -> User endpoints implemented
      - Post.py -> Post endpoints implemented
      - Community.py -> Community endpoints implemented
      - Readme.md -> Endpoint documentation
  
