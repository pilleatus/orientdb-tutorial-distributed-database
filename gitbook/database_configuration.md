# Database Configuration
At this point you should have already installed the docker deamon and you may have already checked the installation with the `docker run hello_world` command.
For the database-instances we will use the official OrientDB package from 
https://hub.docker.com/r/orientdb/orientdb/    

###1. Create an image with the configuration for the servers
First of all we will create an image for a DDBMS-Server. Later it is possible to create some instances with the generated image to setup up our servers.  
To make a new Image with some changes, you can use a Dockerfile. We already added the following dockerfile to the repository:

####Dockerfile:

    FROM orientdb/orientdb:2.1.5
    COPY ./default-distributed-db-config.json /orientdb/config/default-distributed-db-config.json
    
The first Line defines the under-laying image. The second command overwrites the configuration for our distributed system.

####default-distributed-db-config.json:
In this file you can define, where the clusters will be stored.

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
    "customer_europe": {
      "servers" : [ "europe", "usa" ]
    },
    "customer_china": {
      "servers" : [ "china", "europe" ]
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

    docker build -t orientdb/costumer_example:1.0 .
    
With this command we defined a the name as `orientdb/costumer_example` and the version `1.0` the last argument defines where docker searches for a Dockerfile

###2. Create and start (run command) some servers
With the next instruction we run a docker container with the name `usa` using our previously created image. When the server is started you have to choose password for the root user and enter the name for the server, for our first container `usa`. 

    docker run --name usa -it -v /orientdb/config  orientdb/costumer_example:1.0 dserver.sh

The `-it` flags allocate a pseudo-TTY connection to the container.  
The `-v /orientdb/config` defines a volume for the corresponding directory. When you start a Docker Container, the changes on this container getting lost when you stop it afterwards without committing your changes. With Volumes you can specify a directory and save them persistent on your file-system. Through that we have to settup our poassword 
    



    
    




    
blablbl
    
    docker build -t orientdb/costumer_example:1.0 .
    
bllal

    docker run --name usa -v /orientdb/config -it orientdb/costumer_example:1.0 dserver.sh

  

###1. Create and run a container with a volume
First of all we will create a container with a volume.  Other containers can also include such a volume. Therefore we can use some configuration Files with multiple containers. With the following command we set the `/orientdb/config/default-distributed-db-config.json` file as an volume for storing the configuration for servers.
For more information take a look at:
https://docs.docker.com/engine/userguide/containers/dockervolumes/
    
    docker run --name db_server_config -v /orientdb/config/default-distributed-db-config.json -it orientdb/orientdb bash

With the name flag the container name will be specified. And the last term of the command is to set an application running on the container. We will use this container only to set the configuration.  
The first time when you create a orientdb/orientdb container, the deamon will download all necessary under-laying images.

###2. Install a texteditor in the container 
After the container was created. We got through the `-it` flag and the `bash` argument a terminal from the container.  
Now we can install a text-editor to modify the configuration files.
If you are more familiar with the text-editor nano, install it instead. 
    
    apt-get update
    apt-get install vim

If the installation process was successfull, you can commit your changes on the container. Therefore open a other terminal and call:

    docker commit db_server_config
    
Whit this commit the text-editor is already installed, when you start this container next time.

###2. Modifie the orientDB config
In the first terminal we can now open the config file for our schema.
    
    vim /orientdb/config/default-distributed-db-config.json
    
and insert:

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
    "customer_europe": {
      "servers" : [ "europe", "usa" ]
    },
    "customer_china": {
      "servers" : [ "china", "europe" ]
    },
    "*": {
      "servers" : [ "<NEW_NODE>" ]
    }
  }
}

```

###3. Start the Servers
This is an example to start the usa server. Just open an other terminal with:
```
docker run --name usa --volumes-from db_server_config -it orientdb/orientdb dserver.sh
```
setup password and set the name for the server usa


when you are done you can commit the settings with

    docker commit usa
    
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






