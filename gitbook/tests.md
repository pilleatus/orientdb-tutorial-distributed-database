# Tests
Now we can make some tests, for example:

### - Kill one server and show if the model continuous to work.

Before you kill e.g. usa add some records to the usa cluster.<br/>
Now can stop a server (here usa) with the terminal command:
    
    docker stop usa
    
And restart it with (<tt>-a</tt> &#x279c; attach):

    docker start -a usa
    
    
* Are the usa-records still available? Why? Look at the distributed configuration.
* After the restart is the distributed configuration still the same?

### - Add a subclass to Customer.java 
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

* Does it work directly?
* What do you have to consider?


    