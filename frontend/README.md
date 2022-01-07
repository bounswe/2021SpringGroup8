# Frontend

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).



## How to run


### Requirements 

Docker must be installed 


### Running


#### With docker-compose
To build and run the app simply run 
``` 
docker-compose up
```
By default docker-compose.yml file sets the port as 80. After above command container will be build and run. To see the app simply go to http://localhost or http://serveraddress/ where serveraddress is the ip of the deployment server. 

#### With docker build and run
First we need to build the docker image by below command:
``` 
docker build -t nameOfDockerImage .
```
This will give a container with the size 20.9MB.
Then we will run the docker image 
 ```
 docker run -p 80:80 -d -it nameOfDockerImage
 ```
Since this docker container is run with the nginx some configuration is needed if we were to direct page to page using url. If we were to only use application's own router to rout page to page there would have been no problem.


Go to http://localhost/ to see the webapp
