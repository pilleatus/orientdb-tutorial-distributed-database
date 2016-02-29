<a><img align="right" width="15%" src="https://github.com/pilleatus/orientdb-tutorial-distributed-database/blob/master/gitbook/images/setup_db.png?raw=true"/></a>

# Set-up the DDBMS

Now you can run the main method of the Java project.  
Right click on Main.java &#x279c; Run As &#x279c; Java Application.<br/>
In the console you can interact with the application. The output looks like this: 


    welcome, what do you want to do?
    'c'   | connect
    'd'   | disconnect
    'a'   | add
    'r'   | remove
    's'   | show
    'q'   | quit


### connect()

In the first step, we made a remote connection to a database in our docker-container, type 'c' &#x279c; enter.
On Linux systems the available docker IP's appear:

    Please select Database-Server or enter IP address:  (e.g. "172.17.0.3")
    
    Available servers from docker:
    0: 172.17.0.3:china
    1: 172.17.0.2:eu
    2: 172.17.0.4:usa

Now you can choose to which of the three Databases we want connect to.
When you input '2' the application will connect to the usa docker-container with the ip 172.17.0.4. 
Lets look in the source code of the method connection() in Manager.java:

To make a connection over IP to a database we use the keyword "remote:". For authentication we use the user 'root' and the password 'root'. If you have choosen an other password, by setting up the docker-container you have to change it here accordingly. 

<pre style="background-color:#E0E6F8">String sDBName = "WebShopDB";

// OPEN THE DATABASE
OObjectDatabaseTx db = new OObjectDatabaseTx("remote:"+sIP+"/"+sDBName).open("root","root"); 
</pre>	
	
The first time a exception will be thrown, because the database doesn't exists. So we have to check this behaviour before we open the database:  

<pre style="background-color:#E0E6F8">String sDBName = "WebShopDB";
				
// CREATE A SERVER ADMIN CLIENT AGAINST A REMOTE SERVER TO CHECK IF DB EXISTS				
OServerAdmin oSAdmin = new OServerAdmin("remote:"+sIP+"/"+sDBName).connect("root","root");

if(!oSAdmin.existsDatabase())
{
	//create database if not exists	
	oSAdmin.createDatabase(sDBName,"object","plocal").close();
	
	//create clusters 
	...
}
oSAdmin.close();

// OPEN THE DATABASE
OObjectDatabaseTx db = new OObjectDatabaseTx("remote:"+sIP+"/"+sDBName).open("root","root");
</pre>	

 After the generation of the database the method creates clusters for the class Customer.Dynamically for each configured server one. 

<pre style="background-color:#E0E6F8">//create clusters
String sSQL = "create class Customer cluster customer_china,customer_eu,customer_usa";
db.command(new OCommandSQL(sSQL)).execute();
</pre>


in this example the method will create these three clusters:
* customer_eu
* customer_usa
* customer_china

Finally we have to register the class to store objects from the class Customer:
<pre style="background-color:#E0E6F8">// REGISTER THE CLASS ONLY ONCE AFTER THE DB IS OPEN/CREATED
db.getEntityManager().registerEntityClass(Customer.class);
</pre>


### disconnect()
By typing 'd' you disconnect from the database:
<pre style="background-color:#E0E6F8">// disconnect database
if( db!=null && !db.isClosed() )
{
	db.close();
}
</pre>