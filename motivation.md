# Motivation
<a title="By Martin Grandjean [CC BY-SA 3.0 (http://creativecommons.org/licenses/by-sa/3.0)], via Wikimedia Commons" href="https://commons.wikimedia.org/wiki/File%3ASocial_Network_Analysis_Visualization.png"><img align="right" width="35%" alt="Social Network Analysis Visualization" src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/9b/Social_Network_Analysis_Visualization.png/512px-Social_Network_Analysis_Visualization.png"></a>

In today's world processing of Big data takes an important role.

For managing and storing this big amount of data is more than a single database necessary. Due to this reason distributed database systems have been developed.
DDBMS (distributed database management systems) are used if a "Single DBMS" reaches its limits. Reasons for this could be:


1. The volume of data is to big, so it can't stored on a single DBMS 
1. The DBMS can't process the big size of user requests in an acceptable time.
1. Warranty for availability and redundancy of the data are not given on a single system 


DDBMS are installed on multiple servers and provide methods to solve the above challenges. For example if the amount of requests increases, then with horizontal scalability of DDBMS the problem can solved by adding an additional server. With this method a quick reaction on changes is possible and the cost for an additional server is lower than to extend the existing server with better hardware.


With the Multi-Master topology concept of OrientDB, processing of big data is possible. Because of the increasing significance of DDBMS this tutorial was created, to provided a better insight into this topic.   
