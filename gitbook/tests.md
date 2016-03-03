# Tests
Now can make some Test for example:

### - kill one server an show if it continuous to work.

Before you kill e.g. usa add some records to the usa cluster.<br/>
Now can stop a server (here usa) with the terminal command:
    
    docker stop usa
    
and restart it with (<tt>-a</tt> &#x279c; attach)

    docker start -a usa
    
    
* are the usa-records still available? why? Look at the distributed configuration.
* is after the restart the distributed configuration the same?

### - add a subclass to Customer.java 
Add a subclass <tt>Purchases</tt> to each Customer and save it in the database.

```java
public class Purchases
{
  ...
}

public class Customer {
	private Purchases oPurchases;
    ...
}
```

* does it work directly?
* what do you have to consider?


>! Spoiler text sdfjd

    

    