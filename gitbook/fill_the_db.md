<a><img align="right" width="25%" src="https://github.com/pilleatus/orientdb-tutorial-distributed-database/blob/master/gitbook/images/add_customer.png?raw=true"/></a>


# Fill the Database

In the previous chapter you learned how to connect/disconnect to the database. Now you will add some Customer.<br/>
By typing 'a' for add, a random Customer will be added to the default cluster of your connected server.

    and now (c|d|a|r|s|q)?
    a
    How many? (empty for one)
    
    Surname: s163  |  Name: n163  |  Address: city163 str163    <-- added to cluster:default
    
If you are connected to china the default cluster is <TT>customer_china</TT>. The source code for adding is:

<pre style="background-color:#E0E6F8">Customer c = new Customer("s163","n163","str163","city163");
db.save(c);	
</pre>

To show the existing Customers you have to press 's':

    Select clustername (empty for default):
    0: customer_eu
    1: customer_usa
    2: customer_china
 
now you have two options:

1. Select customers from all clusters

    <pre style="background-color:#E0E6F8"><code>String sSQL = "select * from Customer";</code></pre>
  
1. Select customers from specific cluster e.g.: <TT>customer_china</TT>

 <pre style="background-color:#E0E6F8"><code>String sSQL = "select * from cluster:customer_usa";</code></pre>

Booth variants execute a SQL query and saves the returned customers in a list. Finally the method print the customer-list: 

<pre style="background-color:#E0E6F8"><code>List&lt;Customer&gt; lstC = db.query(new OSQLSynchQuery&lt;Customer&gt;(sSQL));
for (Customer c: lstC) 
{
    db.detach(c);
    System.out.println(c);
}
</code></pre>
  
Before printing <tt>c</tt>, a detach is necessary. "*With the detach method all data contained in the object will be copied in the associated object*"[http://orientdb.com/docs/last/Object-Database.html]. Without detach the the values are <tt>null</tt>.
