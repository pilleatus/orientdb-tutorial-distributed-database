#Preperation
For our implementation we need some software components. The authors of this book are using only Linux distributions. Therefore, this tutorial was tested with Ubuntu 14.04 and Mint 17.3.

<a title="By dotCloud, Inc. [Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0)], via Wikimedia Commons" href="https://commons.wikimedia.org/wiki/File%3ADocker_(container_engine)_logo.png"><img align="right" width="35%" alt="Docker (container engine) logo" src="https://upload.wikimedia.org/wikipedia/commons/7/79/Docker_%28container_engine%29_logo.png"/></a>

##Install Docker
With Docker it is possible to package an application with all needed dependencies and upload it to a server. If you were to use such an application you can just download and run it.
To run an application with Docker you first have to install the Docker-Deamon. Meanwhile there are additional tools for managing Docker applications, but they are not necessary for this tutorial. If you are a Linux user just follow the steps on:
https://docs.docker.com/engine/installation

If you are using Windows there will be an extra virtualisation layer between Docker and Windows. That's because Docker needs some Linux-Kernel functions.
If you have time to experiment or if you need this application running on Windows, you're welcome to share your experience (pull request) ;) .
Otherwise its maybe easier to install a virtual Linux System, for example [Virtualbox](https://www.virtualbox.org/), and use it for the tutorial.

##Install Eclipse

The programming part of this tutorial is wrote in Java in combination with the integrated development environment (IDE) Eclipse and a JDK (Java Development Kit). You can download and install the [developer package for Java](https://www.eclipse.org/downloads/) or use another IDE of your choice.

##Clone the repository
<pre style="background-color:black; color:white"><code>git clone https://github.com/pilleatus/orientdb-tutorial-distributed-database.git
</code></pre>
    
or download it:
    
https://github.com/pilleatus/orientdb-tutorial-distributed-database/archive/master.zip
