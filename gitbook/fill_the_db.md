<a><img align="right" width="25%" src="https://github.com/pilleatus/orientdb-tutorial-distributed-database/blob/master/gitbook/images/add_customer.png?raw=true"/></a>


# Fill the Database

In the previous chapter you learned how to connect/disconnect to the database. Now you will add some Customer.<br/>
By typing 'a' for add, a random Customer will be added to the default cluster of your connected server.

    and now (c|d|a|r|s|q)?
    a
    How many? (empty for one)
    
    Surname: s163  |  Name: n163  |  Address: city163 str163    <-- added to cluster:default
    
If you are connected to china the default cluster is <TT>customer_china</TT>.

In the source-code it look like:


