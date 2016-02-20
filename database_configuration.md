# Database Configuration

At this point you should have already installed the docker deamon and you may have already checked the installation with the `docker run hello_world` command.
For the database-instances we will use the official OrientDB package from 
https://hub.docker.com/r/orientdb/orientdb/  


```
docker create -v /config --name db_server_config orientdb/orientdb bash 
```

To start the first Server use the following command:
```
docker run --name db_server_usa orientdb/orientdb 
```



```
docker exec -it db_server_usa bash
```




