# Introduction

In this Tutorial we will setup a Distributed Database with Docker Containers. OrientDB distributed concept is a Multi-Master topology. We will split of a class in three logical [clusters](http://orientdb.com/docs/last/Tutorial-Clusters.html) and store each cluster on two Servers. In this way we improve the reliability and the scalability of our system.

On the OrientDB [Manual Sites](http://orientdb.com/docs/last/Distributed-Architecture.html) are already the basics good explained, to understand how a distributed System works.

In this book we will make an implementation, similar to the example in the OrientDB doc's. 

![Figure 1-1](gitbook/images/schema.png)

This may helps you to understand and test the capabilities and the limits of such a distributed Database.

For setting up some DB-Servers on the same sub-network, are many possibilities for example using virtual machines. In This Tutorial we will use Docker, because it will not generate so much Overhead as an fully virtualized Operating-System. 
In a Docker-Container only the needed processes are running, so there is not so much computing capacity needed.  

If you have never heard about docker, or if you interested, read the well made [docker docs](https://docs.docker.com/).

<div div class="gitbook-link">
<a href="https://pilleatus.gitbooks.io/orientdb-tutorial-distributed-database/content/">Read on Gitbook</a></div>