# Start distributed DB-Servers
At this point you should have already installed the Docker-Deamon and you may have already checked the installation with the `docker run hello_world` command.
For the database-instances we will use the official [Docker OrientDB package](
https://hub.docker.com/r/orientdb/orientdb/).

###1. Create an image with the configuration for the servers
First of all we will create an image for a DDBMS-Server. Later it is possible to create some instances with the generated image to start up some DB-Servers. To create a new image with some changes, you can use a Dockerfile. We already added the following Dockerfile to the repository:

####Dockerfile:
```bash
FROM orientdb/orientdb:latest
#FROM orientdb/orientdb:2.1.5

COPY ./default-distributed-db-config.json /orientdb/config/default-distributed-db-config.json
COPY ./server.sh /orientdb/bin/server.sh
```    
The first line defines the under-laying image. In our case, the latest version of the image "orientdb" from the user "orientdb". The `COPY` commands overwrites the configurations for our distributed system in the container. 

####default-distributed-db-config.json:
In this file you can define the behavior of the distributed servers. Additionally it is possible to define where the clusters will be stored.
For our example we define three clusters:
1. `customer_usa`
2. `customer_eu`
3. `customer_china`
  
The names of the cluster should be defined as```<class>_<node>```. Therefor we will use three servers with following node names: `usa`,`eu`,`china`.
You can get further information [here](
http://orientdb.com/docs/2.0/orientdb.wiki/Distributed-Configuration.html#default-distributed-db-configjson).

Bellow is the edited config-file:
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

#### Create the docker image:
With the following commands we create our new image. Navigate to the docker folder in the repository:
<pre style="background-color:black; color:white"><code>cd docker
</code></pre>
    
And start building the image with:
<pre style="background-color:black; color:white"><code>docker build -t nickname/customer_example:1.0 .
</code></pre>
    
Docker starts now building a new image based on the Dockerfile commands. The name of the image is `nickname/costumer_example` and the version is `1.0`. The last argument defines where docker searches for a Dockerfile

###2. Create and start (run command) some servers
With the next instruction we run a docker container with the name `usa` using our previously created image. When the server is started you have to choose a password for the root user and enter the name for the server, for our first container `usa`. 
<pre style="background-color:black; color:white"><code>docker run --name usa -v /orientdb/config -it nickname/customer_example:1.0 dserver.sh
</code></pre>

The `-it` flags allocate a pseudo-TTY connection to the container. The `-v /orientdb/config` defines a volume for the corresponding directory. When you start a Docker Container, the changes on this container getting lost when you stop it afterwards without committing your changes. With Volumes you can specify a directory and save them persistent on your file-system. In this way we must setup the password and the name for the server only at the first start of our container. For more information take a look at [docker-volumes](https://docs.docker.com/engine/userguide/containers/dockervolumes/).  

Now you can also start the other two servers `eu` and `china`. Just open two new terminals and change in the command the container-name from `usa` to `eu` and at the third server to `china`. When the container is started, you also have to enter a root password again and set the node name accordingly to the container name.

### Dealing with containers
With the `run` command you should have created and started three containers now. And you can go ahead with setting up eclipse.  

If you will stop, start or restart a container you can use the following commands (here container usa).
<pre style="background-color:black; color:white"><code>docker stop usa                             #start container usa
docker start -a usa                         #stop container usa
docker restart usa                          #restart container usa
</code></pre>

Some other useful docker commands:
<pre style="background-color:black; color:white"><code>docker ps                                   #list all running containers
docker images                               #list all images
docker volume ls                            #list all volumes
docker stop $(docker ps -a -q)              #stop all containers
</code></pre>

Cleaning up:
<pre style="background-color:black; color:white"><code>docker rm usa                               #remove container usa
docker rmi nickname/customer_example:1.0    #remove correlated image
docker rm $(docker ps -a -q)                #remove all containers
docker rmi $(docker images -q)              #remove all images
docker volume rm $(docker volume ls -q)     #remove all volumes
</code></pre>

Find more on:
https://docs.docker.com/engine/quickstart/




