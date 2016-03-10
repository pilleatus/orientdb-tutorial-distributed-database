# Conclusion

In this Tutorial you have learned how to setup an distributed database, how to use Docker to run multiple servers and how to access the database via Java. Additional now you know how to save and modify data in the database. The authors of the book hopes that the tutorial was useful to you and that you have learned more about distributed databases. Now you can make your own tests with the distributed database of OrientDB.
    
    
# Troubleshooting

Nowadays OrientDB are developed much further, with each release the 
functional scope is increases.  
With version 2.1.10 the following errors occurred under developing this tutorial:

* <font color="red">error on moving existent database fails because of restricted access to database parent folder</font>  
https://github.com/orientechnologies/orientdb/issues/4891

  To avoid this error, we added a paramter to the file `server.sh`
```bash
-Ddistributed.backupDirectory="/orientdb/backup"
```

* <font color="red">Error on creating cluster on distributed nodes: ids are different</font>  
https://github.com/orientechnologies/orientdb/issues/4767

This WARNING/ERROR disappears after some seconds.

