#!/bin/bash

docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
#docker rmi $(docker images -q)
docker rmi $(docker images | grep 'orientdb/costumer_example' | awk {'print $3'})

docker build -t orientdb/costumer_example:1.0 .
docker run --name usa -it orientdb/costumer_example:1.0 dserver.sh


