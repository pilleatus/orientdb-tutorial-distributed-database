# Customer Example
One example application for a DDBMS could be a international webshop. For a better access time to the Server, there are three Servers distributed on the continents. A server in the USA, one in the EU and one in China.
These structure is similar to the example in the official OrientDB docs. Instead of class Client, we will use a the class name Customer.
To decrease the risk for a data lost, well will store each cluster on a second server.



![Figure 1-1](https://github.com/pilleatus/orientdb-tutorial-distributed-database/blob/master/gitbook/images/schema.png?raw=true)

By default OrientDB creates a default cluster per class. Therefor all instances will be stored in this cluster. When you start some distributed Servers and you don't change the settings, every server will store all clusters.  
For example when you register the class Client, the server will create a default cluster named "client" and this cluster will reduplicated on all running servers.  
To change this behavior, we have to modify some settings in this tutorial.


