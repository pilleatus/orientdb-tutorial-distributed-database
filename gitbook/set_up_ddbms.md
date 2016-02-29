# Set-up the DDBMS

Now you can run the main of the Java project.
Right click on Main.java -> Run As -> Java Application.
In the console you can interact with the application. The output looks like this: 


    welcome, what do you want to do?
    'c'   | connect
    'd'   | disconnect
    'a'   | add
    'r'   | remove
    's'   | show
    'q'   | quit


In the first step, we made a remote connection to a database in our docker-container, with 'c' -> enter.
On Linux systems the available docker IP's appear:

    Please select Database-Server or enter IP address:  (e.g. "172.17.0.3")
    
    Available servers from docker:
    0: 172.17.0.3:china
    1: 172.17.0.2:eu
    2: 172.17.0.4:usa

Now we can choose to with of the three Databases we want to connect.
When we input "2" the application will connect to "172.17.0.4:usa". 
Lets look in the source code of the method connection() in Manager.java:


    String sDBName = "WebShopDB";
	
	// CREATE A SERVER ADMIN CLIENT AGAINST A REMOTE SERVER TO CHECK IF DB EXISTS				
	OServerAdmin oSAdmin = new OServerAdmin("remote:"+sIP+"/"+sDBName).connect("root","root");
	
	if(!oSAdmin.existsDatabase())
	{
		//create database if not exists	
		System.out.println("DB dosen't exist --> Create");
		oSAdmin.createDatabase(sDBName,"object","plocal").close();
		
		// open database
		db = new OObjectDatabaseTx("remote:"+sIP+"/"+sDBName).open("root","root");	
		
		//Add Cluster
		String sClusters="";
		
		for (String sDockerName : dockerIPs.values()) {
			String sName = "customer_"+sDockerName;
			System.out.println("add cluster "+sName);
			//db.command(new OCommandSQL(sSQL)).execute();
			db.addCluster(sName);
			sClusters += (sClusters.isEmpty())?""+sName:","+sName;
		}
		
		String sSQL = "create class Customer cluster "+ sClusters;
		System.out.println(sSQL);
		db.command(new OCommandSQL(sSQL)).execute();
		db.close();
	}
	oSAdmin.close();
	
	// open database
	db = new OObjectDatabaseTx("remote:"+sIP+"/"+sDBName).open("root","root");	
	
	// REGISTER THE CLASS ONLY ONCE AFTER THE DB IS OPEN/CREATED
	db.getEntityManager().registerEntityClass(Customer.class);
	
	System.out.println("connect to "+dockerIPs.get(sCurrentIP));









, witch are running on the privius started docker container. 






<hr/><hr/><hr/>

now we can start 

add 
show 
remove
