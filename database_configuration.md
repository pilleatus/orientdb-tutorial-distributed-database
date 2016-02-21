# Database Configuration

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

###2. Modifie the orientDB config
In the first terminal we can now open 
You already should have this terminal connection to the container. Open the 
To start the first Server use the following command:
```
docker run --name db_server_usa orientdb/orientdb dserver.sh
```



```
docker exec -it db_server_usa bash
```




