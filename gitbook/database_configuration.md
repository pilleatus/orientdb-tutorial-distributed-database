# Database Configuration

#Dockerfile:

    FROM orientdb/orientdb:2.1.5
    COPY ./default-distributed-db-config.json /orientdb/config/default-distributed-db-config.json
    

At this point you should have already installed the docker deamon and you may have already checked the installation with the `docker run hello_world` command.
For the database-instances we will use the official OrientDB package from 
https://hub.docker.com/r/orientdb/orientdb/  

###1. Create and run a container with a volume
First of all we will create a container with a volume. When you start a Docker Container, the changes on this container getting lost when you stop it afterwards without committing your changes. With Volumes you can specify a directory or files and save them persistent on your file-system. Other containers can also include such a volume. Therefore we can use some configuration Files with multiple containers. With the following command we set the `/orientdb/config/default-distributed-db-config.json` file as an volume for storing the configuration for servers.
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





