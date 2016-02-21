# Database Configuration

At this point you should have already installed the docker deamon and you may have already checked the installation with the `docker run hello_world` command.
For the database-instances we will use the official OrientDB package from 
https://hub.docker.com/r/orientdb/orientdb/  

###1. Create a container with a volume
First of all we will create a container with the volume \config. When you start a Docker Container, the changes on this container getting lost when you stop it afterwards. With Volumes you can specify a directory or files and save them persistent on your filesystem. with the following command we create such an volume for storing the configuration for the DDBMS.
```
docker create -v /config --name db_server_config orientdb/orientdb /bin/true
```
With the name flag the container name will be specified. And the last term of the command is to set an application running on the container. We will use this container only to set the configuration. Therefor we use the /bin/true.
The first time when you create a orientdb/orientdb container, the deamon will download all necessary under-laying images.

###2. modifie the orientDB config


To start the first Server use the following command:
```
docker run --name db_server_usa orientdb/orientdb dserver.sh
```



```
docker exec -it db_server_usa bash
```




