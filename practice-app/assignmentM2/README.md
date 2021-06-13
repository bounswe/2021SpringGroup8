
# How to run this web application

## Cloning the application 

First one needs to clone the web-app into his/her local machine

```
git clone https://github.com/bounswe/2021SpringGroup8.git
```


## How to run using Docker


First docker image needs to be created. In the directory Dockerfile located running below command will create a docker image with repository name of assignment2/assignment2:

```
docker build . -t assignment2/assignment2
```

After the docker image created one can run the application at port 8000 with the command : 

```
docker run -d -p 8000:8000 assignment2/assignment2
```
