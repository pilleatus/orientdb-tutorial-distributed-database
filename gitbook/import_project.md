In the following chapters we will access and modify the database from Eclipse via Java.
For a better start, the authors of the book have already created an example Java-Project.
Afterwards the methods of the example will be explained. If you understood this example, you can extend or change the example project and run your own tests.
## Import Project to Eclipse

First of all you have to import the example project into eclipse:

**1.** Start Eclipse <br/>
**2.** File &#x279c; Import &#x279c; General &#x279c; Existing Projects to Workspace<br/>
**3.** Select the "orientdb-tutorial-distributed-database" folder from the downloaded repository

![](./images/project-import.png)

After the import, your project explorer should look similar to this one:

![](./images/project-explorer.png)

In the main package of the project there are three classes:
    
* <tt>Customer</tt>:&ensp; Defines a simple person with name, address...
* <tt>Main</tt>: &ensp; &ensp; &ensp; &ensp;Entry-point for our program
* <tt>Manager</tt>: &ensp;&ensp;Various methods for connecting and modifying the database




The customer example is a Java-Object-Project, so we used the Object-API from OrientDB with the corresponding libraries from [here](http://orientdb.com/download-previous/).