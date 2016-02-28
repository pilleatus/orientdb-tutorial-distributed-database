# Database Configuration
At this point you should have already installed the docker deamon and you may have already checked the installation with the `docker run hello_world` command.
For the database-instances we will use the official OrientDB package from 
https://hub.docker.com/r/orientdb/orientdb/

###1. Create an image with the configuration for the servers
First of all we will create an image for a DDBMS-Server. Later it is possible to create some instances with the generated image to start up some DB-Servers.  
To create a new Image with some changes, you can use a Dockerfile. We already added the following Dockerfile to the repository:

####Dockerfile:

    FROM orientdb/orientdb:latest
    #FROM orientdb/orientdb:2.1.5

    COPY ./default-distributed-db-config.json /orientdb/config/default-distributed-db-config.json
    COPY ./server.sh /orientdb/bin/server.sh
    
The first Line defines the under-laying image, in this case from user "orientdb" the image "orientdb" and the latest version. The first COPY command overwrites the configuration for our distributed system. 

####default-distributed-db-config.json:
In this file you can define the behavior of the distributed Servers. Additionally it is possible to define where the clusters will be stored.
For our example we define three clusters (customer_usa, customer_euwe want split the class Customer, therewith the data will be stored in 

```json
{
  "autoDeploy": true,
  "hotAlignment": false,
  "readQuorum": 1,
  "writeQuorum": 2,
  "failureAvailableNodesLessQuorum": false,
  "readYourWrites": true,
  "clusters": {
    "internal": {
    },
    "index": {
    },
    "customer_usa": {
      "servers" : [ "usa", "china" ]
    },
    "customer_eu": {
      "servers" : [ "eu", "usa" ]
    },
    "customer_china": {
      "servers" : [ "china", "eu" ]
    },
    "*": {
      "servers" : [ "<NEW_NODE>" ]
    }
  }
}
```

With the following commands we create our new image. Navigate to the docker folder in the repository

    cd docker
    
And start building the image with:

    docker build -t nickname/customer_example:1.0 .
    
Docker starts now building a new image based on the Dockerfile commands. The name of the image is `nickname/costumer_example` and the version is `1.0`. The last argument defines where docker searches for a Dockerfile

###2. Create and start (run command) some servers
With the next instruction we run a docker container with the name `usa` using our previously created image. When the server is started you have to choose a password for the root user and enter the name for the server, for our first container `usa`. 

    docker run --name usa -v /orientdb/config -it orientdb/customer_example:1.0 dserver.sh

The `-it` flags allocate a pseudo-TTY connection to the container.  
The `-v /orientdb/config` defines a volume for the corresponding directory. When you start a Docker Container, the changes on this container getting lost when you stop it afterwards without committing your changes. With Volumes you can specify a directory and save them persistent on your file-system. Therefore we must setup the password and the name for the server only at the first start of our container.  
For more information take a look at:
https://docs.docker.com/engine/userguide/containers/dockervolumes/  

Now you can also start the other two servers `eu` and the `china`. Just open a new terminal and change the container-name in the command from `usa` to `eu` and at the third server to `china`. When the container is started, you also have to enter a root password again and set the node name accordingly to the container name.


---------------------------------------------------------------------------------
    
next time you can start the server with 

    docker start usa
    
some other useful docker commands:

    docker ps                               #list all running containers
    docker stop $(docker ps -a -q)          #stop all containers
   
cleaning up everything:
    
    docker rm $(docker ps -a -q)            #remova all containers
    docker images                           #list all images
    docker rmi <id>                         #remove image by id
    docker rmi $(docker images -q)          #remove all images
    
    docker volume rm $(docker volume ls -q) #remove all volumes






