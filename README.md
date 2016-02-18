# orientdb-tutorial-distributed-database

In this Tutorial we will setup a Distributed Database with Docker Containers. OrientDB distributed concept is a Multi-Master topology. We will split of a class in three logical clusters and store each cluster on two Servers. In this way we improve the reliability and the scalability of our system.

On the OrientDB Manual Sites are already the basics good explained, to understand how a distributed System works.
http://orientdb.com/docs/2.0/orientdb.wiki/Distributed-Architecture.html   
In this Tutorial we will make an implementation of the example in the doc's. 

<img src="http://www.orientdb.org/images/distributed-sharding-replica-class.png" align="left">

This may helps you to understand and test the capabilities and the limits of such a distributed Database.

For setting up some DB-Servers on the same sub-network, are many possibilities for example using virtual machines. In This Tutorial we will use Docker, because it will not generate so much Overhead as an fully virtualized Operating-System. 
In a Docker-Container only the needed processes are running, so there is not so much computing capacity needed comparing to use some virtual-machines.

If you have never heard about docker, or if you interested, read the well made doc's on https://docs.docker.com/.