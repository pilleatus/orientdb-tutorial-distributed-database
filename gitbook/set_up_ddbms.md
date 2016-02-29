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










, witch are running on the privius started docker container. 






<hr/><hr/><hr/>

now we can start 

add 
show 
remove
