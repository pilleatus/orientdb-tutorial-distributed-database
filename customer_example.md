# Customer Example
One example application for a DDBMS could be a international webshop. For a better access time to the Server, there are three Servers distributed on the continents. A server in the USA, one in the EU and one in China.
These structure is similar to the example in the official OrientDB docs. Instead of class Client, we will use a the class name Costumer.
To decrease the risk for a data lost, well will store each cluster on a second server.


!!!!! costumer != customer 

![Figure 1-1](https://github.com/pilleatus/orientdb-tutorial-distributed-database/blob/master/images/schema.png?raw=true)

 
