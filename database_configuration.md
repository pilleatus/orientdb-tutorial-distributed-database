# Database Configuration

At this point you should have already installed the docker deamon and you may have already checked the installation with the `docker run hello_world` command.
For the database-instances we will use the official OrientDB package from 
https://hub.docker.com/r/orientdb/orientdb/  

###1. Create a container with a volume
First of all we will create a container with the volume /config. When you start a Docker Container, the changes on this container getting lost when you stop it afterwards. With Volumes you can specify a directory or files and save them persistent on your filesystem. With the following command we set the /config directory as an volume for storing the configuration for the DDBMS.
For more information take a look at:
https://docs.docker.com/engine/userguide/containers/dockervolumes/
    
    docker create -v /config --name db_server_config orientdb/orientdb /bin/true

With the name flag the container name will be specified. And the last term of the command is to set an application running on the container. We will use this container only to set the configuration. Therefor we use the /bin/true.
The first time when you create a orientdb/orientdb container, the deamon will download all necessary under-laying images.

###2. Install a texteditor in the container 
Now can we start our created container by:

    docker start db_server_config /bin/bash

It is not necessary to start the db-server at the moment, we just take a simple application like /bin/bash for keeping the container running.  
In the next step we can make a terminal connection to the container and install a text-editor to modifie the config files. If you are more familiar with nano, install it instead. 
    
    docker exec -it db_server_config bash
    apt-get update
    apt-get install vim

If the installation process was successfull, you can commit your changes on the container. Therefore open a other terminal and call:

    docker commit db_server_config

###2. Modifie the orientDB config
You already should have this terminal connection to the container. Open the 
To start the first Server use the following command:
```
docker run --name db_server_usa orientdb/orientdb dserver.sh
```



```
docker exec -it db_server_usa bash
```




