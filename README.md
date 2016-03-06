# Introduction

In this Tutorial we will setup a Distributed Database with Docker Containers. OrientDB distributed concept is a Multi-Master topology. We will split of a class in three logical [clusters](http://orientdb.com/docs/last/Tutorial-Clusters.html) and store each cluster on two servers. In this way we improve the reliability and the scalability of our system.

On the OrientDB [manual sites](http://orientdb.com/docs/last/Distributed-Architecture.html) the basics to understand how a distributed system works are already well explained.

In this book we will create an implementation, similar to the example in the OrientDB doc's. 

![Figure 1-1](gitbook/images/schema.png)

This may help you understand and test the capabilities and the limits of such a distributed database.

For setting up some DB-Servers on the same sub-network there are many possibilities, for example using virtual machines. In this Tutorial we will use Docker, since it will not generate so much overhead as a fully virtualized operating system. 
In a Docker-Container only the needed processes are running, therefore not so much computing capacity is needed.  

If you have never heard about docker, or if you interested, read the well made [docker docs](https://docs.docker.com/).

<div div class="gitbook-link">
<a href="https://pilleatus.gitbooks.io/orientdb-tutorial-distributed-database/content/">Read on Gitbook</a></div>