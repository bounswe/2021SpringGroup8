# Frontend

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).



## How to run


### Requirements 

Node and npm must be installed. 


### Running
#### Without Docker
In the project directory, you can run:
```
npm install
```
```
npm start
```
Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.
Login page can be found on  [http://localhost:3000/login](http://localhost:3000/login)
The page will reload if you make edits.\
You will also see any lint errors in the console.

#### With docker
First we need to build the docker image by below command:
``` 
docker build -t nameOfDockerImage .
```
This will give a container with the size 20.9MB.
Then we will run the docker image 
 ```
 docker run -p 3000:80 -d --rm -it nameOfDockerImage
 ```
Since this docker container is run with the nginx some configuration is needed if we were to direct page to page using url. If we were to only use application's own router to rout page to page there would have been no problem.

Enter the container by the command to modify nginx conf file:
 ```
 docker exec -it containerId bin/sh
 ```
Inside of the container go to /etc/nginx/conf.d/ and append the below lines to the
default.conf file :
>
>
>
>
>
           location / {
            root   /usr/share/nginx/html;
            index  index.html index.htm;
            if(!-e $request_filename){
            rewrite ^(.*)$ /index.html break;
                }
            }
>
>
>
And after that delete the line:
>
>
>
>
>
>
       location / {
                    root   /usr/share/nginx/html;
                    index  index.html index.htm;
                    }
>
Then restarting the nginx will give the desired result :
```
nginx -s reload -c /etc/nginx/nginx.conf
``` 
Go to http://localhost:3000 to see the webapp
