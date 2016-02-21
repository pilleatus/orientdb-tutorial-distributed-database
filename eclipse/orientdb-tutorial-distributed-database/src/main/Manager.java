package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.orientechnologies.orient.client.remote.OServerAdmin;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

import java.util.List;
import java.util.Random;



public class Manager {

		private OObjectDatabaseTx db;
		private BufferedReader br;
	
		void startLoop(){
	
			System.out.println("welcome, what do you want to do?");
			System.out.println("'a' | add");
			System.out.println("'r' | remove");
			System.out.println("'s' | show");
			System.out.println("'q' | quit");
			
			// todo connect/dis
			
			String sInput = "";
			br = new BufferedReader(new InputStreamReader(System.in));

	        try{
	        	sInput = br.readLine();
	        } catch(Exception e){}
			
			
			if(sInput == null) sInput = "";
			
			// connect to database
			connect();
			
			while (!sInput.equals("quit")&&!sInput.equals("q")) 
			{
				switch (sInput) 
				{
					case "add":    case "a": add();     break;
					case "remove": case "r": remove();  break;
					case "show":   case "s": show();    break;
					default: System.out.println("???"); break;
				}
				System.out.println("____________________________________________________________________\nand now (a|r|s|q)?");
				try{
		        	sInput = br.readLine();
		        } catch(Exception e){}
			}
			
			// disconnect database
			close();
			
			System.out.println("bye");
		}
		
		void connect()
		{			
			
			
			
			// wählen welcher docker container
			//  docker network inspect bridge 
			//  docker inspect -f '{{ .NetworkSettings.Name }}' 6950151d643a
			//  docker ps -q
			
			try 
			{
				String sIP="172.17.0.3";
				
				// CREATE A SERVER ADMIN CLIENT AGAINST A REMOTE SERVER TO CHECK IF DB EXISTS				
				OServerAdmin oSAdmin = new OServerAdmin("remote:"+sIP+"/test").connect("root","root");
							
				if(!oSAdmin.existsDatabase())
				{
					//create database if not exists	
					System.out.println("DB dosen't exist --> Create");
					oSAdmin.createDatabase("test","object","plocal").close();
				}
				oSAdmin.close();
				
				db = new OObjectDatabaseTx("remote:"+sIP+"/test").open("root","root");
				
				// REGISTER THE CLASS ONLY ONCE AFTER THE DB IS OPEN/CREATED
				db.getEntityManager().registerEntityClass(Customer.class);

				System.out.println("connect");
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		void close()
		{
			// disconnect database
			if(db != null)
			{
				db.close();
				System.out.println("disconnect");
			}
		}
		

		void add()
		{
			// generate random customer and add it to the database
			String sR = String.valueOf(new Random().nextInt(900)+100);
			Customer c = new Customer("s"+sR,"n"+sR,"str"+sR,"city"+sR);
			db.save(c);
			System.out.println(c+ "    <-- added");
		}
		
		void remove()
		{
			// get all customers from database
			List<Customer> lstC = db.query(new OSQLSynchQuery<Customer>("select * from Customer"));
			
			if(lstC.isEmpty())
			{
				System.out.println("No Customers in DB");
				return;
			}
			
			// choose random customer and remove it from database
			Customer cR = lstC.get(new Random().nextInt(lstC.size()));
			db.detach(cR);
			System.out.println(cR+ "    <-- removed");
			db.delete(cR);
		}
		
		void show()
		{
			// get all customers from database and print it
			List<Customer> lstC = db.query(new OSQLSynchQuery<Customer>("select * from Customer"));
			
			if(lstC.isEmpty())
			{
				System.out.println("No Customers in DB");
				return;
			}
			
			for (Customer c: lstC) 
			{
				db.detach(c);
				System.out.println(c);
			}
		}
		
}
