#Preperation
For our implementation we need some software components. The Authors of this book are using only Linux distributions. Therefore this tutorial is tested with Ubuntu 14.04 and Mint 17.3.

<a title="By dotCloud, Inc. [Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0)], via Wikimedia Commons" href="https://commons.wikimedia.org/wiki/File%3ADocker_(container_engine)_logo.png"><img align="right" width="35%" alt="Docker (container engine) logo" src="https://upload.wikimedia.org/wikipedia/commons/7/79/Docker_%28container_engine%29_logo.png"/></a>

##install Docker
With docker it is possible to package a application with all needed dependencies and upload it to a server. If you will use such a application you can just download and start it.
To run a application with Docker you first have to install the Docker-Deamon. Meanwhile there are additional tools for managing docker applications. But they are not necessary for this Tutorial. When you are a linux user just follow the steps on:
https://docs.docker.com/engine/installation

If you are using Windows there will be an extra virtualisation Layer between Docker and Windows. That's because docker needs some Linux-Kernel functions.
If you have time to experiment or if you need this application running on Windows, you're welcome to share your experience (pull request) ;).
Otherwise its maybe easier to install a virtual Linux System, for example Virtualbox and use it for the tutorial.

##install Eclipse

The programming part of this tutorial is wrote in the programming language Java in combination with the integrated development environment (IDE) Eclipse and a JDK (Java Development Kit). You can download and install the developer package for Java here: https://www.eclipse.org/downloads/ and https://wiki.eclipse.org/Eclipse/Installation or use another IDE of your choice.

##clone repository
<pre style="background-color:black; color:white"><code>git clone https://github.com/pilleatus/orientdb-tutorial-distributed-database.git
</code></pre>
    
or download it compressed:
    
https://github.com/pilleatus/orientdb-tutorial-distributed-database/archive/master.zip
