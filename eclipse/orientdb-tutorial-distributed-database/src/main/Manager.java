package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.orientechnologies.orient.client.remote.OServerAdmin;
import com.orientechnologies.orient.core.query.OQuery;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.query.OSQLQuery;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import com.sun.jna.platform.unix.X11.XClientMessageEvent.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.regex.Pattern;

import javax.swing.plaf.SliderUI;
import javax.xml.crypto.dsig.keyinfo.KeyValue;



public class Manager {

		private OObjectDatabaseTx db;
		private BufferedReader br;
		private LinkedHashMap<String,String> dockerIPs; // IP::DockerName
		private String sCurrentIP;
	
		void startLoop(){
	
			System.out.println("welcome, what do you want to do?");
			System.out.println("'c'   | connect");
			System.out.println("'d'   | disconnect");
			System.out.println("'a'   | add");
			System.out.println("'r'   | remove");
			System.out.println("'s'   | show");
			System.out.println("'q'   | quit");			
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
					case "connect":		case "c": connect();    	break;
					case "disconnect":	case "d": disconnect();		break;
					case "add":			case "a": add();     		break;
					case "remove":		case "r": remove();  		break;
					case "show":		case "s": show();    		break;
					default: System.out.println("???"); 			break;
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
			
			// get available docker names an IPs
			dockerIPs = new LinkedHashMap<String,String>();
			if (System.getProperty("os.name").startsWith("Windows")) 
			{
		        // includes: Windows 2000,  Windows 95, Windows 98, Windows NT, Windows Vista, Windows XP
		    } 
			else 
		    {
		        // everything else
				try {
					String cmdSH = 
							"#!/bin/bash \n"+
							"array=( $(docker ps | awk '{if(NR>1) print $NF}') ) \n"+
							"for i in ${array[@]} \n"+
							"do \n"+
							"        echo $(docker inspect --format '{{ .NetworkSettings.IPAddress }}' ${i})'::'${i}\n"+
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
			
			// select server
			String sIP = "";
			Pattern p = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
			br = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Please select Database-Server or enter IP address:  (e.g. \"172.17.0.3\")\n");
			if(!dockerIPs.isEmpty())
			{
				System.out.println("Available servers from docker:");
				int i = 0;
				for (Map.Entry<String, String> entry : dockerIPs.entrySet())
				{
					System.out.println(i+": "+entry.getKey()+ ":" + entry.getValue());
					i++;
				}
			}
			while (true) {
				try {
					sIP = br.readLine();
					
					if(sIP.length()<3)
					{
						int i = Integer.parseInt(sIP);
						sIP = dockerIPs.keySet().toArray()[i].toString();			
					}
					sCurrentIP=sIP;
				} catch (Exception e) {}
				if(sIP != null && !sIP.isEmpty() && p.matcher(sIP).find()) break;
				System.out.println("???\n");
			}
			
			//connect (create) database
			try 
			{
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
				System.out.println("disconnect from "+dockerIPs.get(sCurrentIP));
			}
		}
		
		void add()
		{
			if(db==null || db.isClosed()) connect();
						
			int iHowMany = 1;
			br = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("How many? (empty for one)");
			try {
				iHowMany =  Integer.parseInt(br.readLine()) ;
			} catch (Exception e) {
				iHowMany = 1;
			}
			
			//Select Cluster
			String sSelCluster = selectCluster();
			
			// generate random customer and add it to the database
			for (int i = 0; i < iHowMany; i++) {
				try {
					String sR = String.valueOf(new Random().nextInt(900) + 100);
					Customer c = new Customer("s" + sR, "n" + sR, "str" + sR, "city" + sR);
					if(sSelCluster == null)
					{
						db.save(c);						
						System.out.println(c + "    <-- added");
					}else {
						db.save(c,sSelCluster);
						System.out.println(c + "    <-- added to "+sSelCluster);
					}
				} catch (Exception e) {
					System.out.println(e);				
				}
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
				// get all customers from database and all clusters
				List<Customer> lstC = db.query(new OSQLSynchQuery<Customer>("select * from Customer"));
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
			
			//Select Cluster
			String sSelCluster = selectCluster();
			String sSQL = "";
			
			if(sSelCluster == null || sSelCluster.isEmpty())
			{
				sSQL = "select * from Customer";
			}
			else
			{
				sSQL = "select * from cluster:"+sSelCluster;
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
			
		String selectCluster()
		{
			try {
				String sSelCluster = null;
				
				Collection<String> lClusterNames = db.getClusterNames();
				LinkedHashMap<Integer,String> mapCClusters = new LinkedHashMap<>();
				
				System.out.println("Select clustername (empty for default):");
				int i =0;
				for (String s : lClusterNames) {
					if(s.contains("customer"))
					{
						System.out.println(i+": "+s);
						mapCClusters.put(i,s);
						i++;
					}
				}
				
				if(!mapCClusters.isEmpty())
				{
					while (sSelCluster == null) {
						try {
							String sLine = br.readLine();
							int iSel = Integer.parseInt(sLine);
							sSelCluster = mapCClusters.get(iSel);
							System.out.println("selected cluster: "+sSelCluster);
							break;
						} catch (Exception e) 
						{
							return null;
						}
					}
				}
				return sSelCluster;
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
			
		}


}
