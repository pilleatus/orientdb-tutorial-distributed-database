package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.orientechnologies.orient.client.remote.OServerAdmin;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.regex.Pattern;

import javax.xml.crypto.dsig.keyinfo.KeyValue;



public class Manager {

		private OObjectDatabaseTx db;
		private BufferedReader br;
		private Map<String,String> dockerIPs;
	
		void startLoop(){
	
			System.out.println("welcome, what do you want to do?");
			System.out.println("'c' | connect");
			System.out.println("'d' | disconnect");
			System.out.println("'a' | add");
			System.out.println("'r' | remove");
			System.out.println("'s' | show");
			System.out.println("'q' | quit");
			
			String sInput = "";
			br = new BufferedReader(new InputStreamReader(System.in));

	        try{
	        	sInput = br.readLine();
	        } catch(Exception e){}
			
			
			if(sInput == null) sInput = "";
			
			
			while (!sInput.equals("quit")&&!sInput.equals("q")) 
			{
				switch (sInput) 
				{
					case "connect":		case "c": connect();    break;
					case "disconnect":	case "d": disconnect();	break;
					case "add":			case "a": add();     	break;
					case "remove":		case "r": remove();  	break;
					case "show":		case "s": show();    	break;
					default: System.out.println("???"); 		break;
				}
				System.out.println("____________________________________________________________________\nand now (c|d|a|r|s|q)?");
				try{
		        	sInput = br.readLine();
		        } catch(Exception e){}
			}
			
			// disconnect database
			disconnect();
			
			System.out.println("bye");
		}
		
		void connect()
		{		
			disconnect();
			
			System.out.println("###############  CONNECT  ################");
			
			dockerIPs = new HashMap<String,String>();
			
			if (System.getProperty("os.name").startsWith("Windows")) {
		        // includes: Windows 2000,  Windows 95, Windows 98, Windows NT, Windows Vista, Windows XP
		    } else {
		        // everything else
				try {
					// get available docker names an IPs
					String cmdSH = 
							"#!/bin/bash \n"+
							"array=( $(docker ps | awk '{if(NR>1) print $NF}') ) \n"+
							"for i in ${array[@]} \n"+
							"do \n"+
							"        echo ${i}'::'$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' ${i}) \n"+
							"done\n";
							
					String[] cmd = new String[]{"/bin/bash","-c",cmdSH};
					Process p = Runtime.getRuntime().exec(cmd);
					p.waitFor();
				    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

				    String line = "";			
				    while ((line = reader.readLine())!= null) {    	
				    	dockerIPs.put(line.split("::")[0],line.split("::")[1]);
				    }
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    	
		    } 
			

			System.out.println("Please enter the Database-Server-IP address:  (e.g. \"172.17.0.3\")");

			if(!dockerIPs.isEmpty())
			{
				System.out.println("Available servers from docker:");
				for (Map.Entry<String, String> entry : dockerIPs.entrySet())
				{
					System.out.println(entry.getValue()+ ":" + entry.getKey());
				}
			}
			
			String sIP = "";
			br = new BufferedReader(new InputStreamReader(System.in));
		    Pattern p = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");

			
			while (true) {
				try {
					sIP = br.readLine();
				} catch (Exception e) {
				}
				if(sIP != null && !sIP.isEmpty() && p.matcher(sIP).find()) break;
				System.out.println("???");
			}
			

			
			try 
			{
				
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
		
		void disconnect()
		{
			// disconnect database
			if(db != null && !db.isClosed())
			{
				db.close();
				System.out.println("##############  DISCONNECT  ##############");
			}
		}
		
		void add()
		{
			if(db==null || db.isClosed()) connect();
			
			//System.out.println("#################  ADD   #################");
			
			System.out.println("How many? (empty for one)");
			int iHowMany = 1;
			
			br = new BufferedReader(new InputStreamReader(System.in));
			
			try {
				iHowMany =  Integer.parseInt(br.readLine()) ;
			} catch (Exception e) {
				iHowMany = 1;
			}
			
			for (int i = 0; i < iHowMany; i++) {
				// generate random customer and add it to the database
				String sR = String.valueOf(new Random().nextInt(900) + 100);
				Customer c = new Customer("s" + sR, "n" + sR, "str" + sR,
						"city" + sR);
				db.save(c);
				System.out.println(c + "    <-- added");
			}
			
			
			
		}
		
		void remove()
		{
			if(db==null || db.isClosed()) connect();
			
			System.out.println("How many? (empty for one)");
			int iHowMany = 1;
			
			br = new BufferedReader(new InputStreamReader(System.in));
			
			try {
				iHowMany =  Integer.parseInt(br.readLine()) ;
			} catch (Exception e) {
				iHowMany = 1;
			}
						
			for (int i = 0; i < iHowMany; i++) {
				// get all customers from database
				List<Customer> lstC = db.query(new OSQLSynchQuery<Customer>(
						"select * from Customer"));
				if (lstC.isEmpty()) {
					System.out.println("No Customers in DB");
					return;
				}
				// choose random customer and remove it from database
				Customer cR = lstC.get(new Random().nextInt(lstC.size()));
				db.detach(cR);
				System.out.println(cR + "    <-- removed");
				db.delete(cR);
			}
		}
		
		void show()
		{
			if(db==null || db.isClosed()) connect();
			
			System.out.println("#################  SHOW  #################");
			
			Collection<String> lClusterNames = db.getClusterNames();
			
			System.out.println("Select clustername or leave it empty to show all records of class:");
			for (String s : lClusterNames) {
				if(s.contains("customer")) System.out.println(s);
			}
			System.out.println("");
			
			String sClusterName = "";
			br = new BufferedReader(new InputStreamReader(System.in));
			
			try {
				sClusterName = br.readLine();
			} catch (Exception e) {
				sClusterName = "";
			}
			
			String sSQL = "";
			
			if(sClusterName.isEmpty()){
				sSQL = "select * from Customer";
			}
			else
			{
				sSQL = "select * from cluster:"+sClusterName;
			}
			
			// get customers from database and print it
			List<Customer> lstC = null;
			try {
				lstC = db.query(new OSQLSynchQuery<Customer>(sSQL));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(lstC == null)
			{
				System.out.println("error");
				return;
			}
			
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
