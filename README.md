# Introduction

In this Tutorial we will setup a Distributed Database with Docker Containers. OrientDB distributed concept is a Multi-Master topology. We will split of a class in three logical clusters and store each cluster on two Servers. In this way we improve the reliability and the scalability of our system.

On the OrientDB Manual Sites are already the basics good explained, to understand how a distributed System works.
http://orientdb.com/docs/2.0/orientdb.wiki/Distributed-Architecture.html   
In this Tutorial we will make an implementation, similar to the example in the OrientDB doc's. 

![Figure 1-1](https://github.com/pilleatus/orientdb-tutorial-distributed-database/blob/master/gitbook/images/schema.png?raw=true)

This may helps you to understand and test the capabilities and the limits of such a distributed Database.

For setting up some DB-Servers on the same sub-network, are many possibilities for example using virtual machines. In This Tutorial we will use Docker, because it will not generate so much Overhead as an fully virtualized Operating-System. 
In a Docker-Container only the needed processes are running, so there is not so much computing capacity needed.  
If you have never heard about docker, or if you interested, read the well made doc's on https://docs.docker.com/.