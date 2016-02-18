#Preperation
For our implementation we need some software components. The Authors of this book are using only Linux distributions. Therefore this tutorial is tested with Ubuntu 14.04 and Mint 17.3.

##install Docker
<img src="https://www.docker.com/sites/all/themes/docker/assets/images/logo.png" align="right" width="35%">
With docker it is possible to package a application with all needed dependencies and upload it to a server. If you will use such a application you can just download it and start it.
To run a application with Docker you first have to install the Docker-Deamon. Meanwhile there are additional tools for managing docker applications. But they are not necessary for this Tutorial. When you are a Linux user just follow the steps on:
https://docs.docker.com/engine/installation

If you are using Windows there will be an extra virtualisation Layer between Docker and Windows. That's because docker needs some Linux-Kernel functions.
If you have time to experiment or if you need this application running on Windows, you're welcome to share your experience (pull request) ;).
Otherwise its maybe easier to install a virtual Linux System with for example Virtualbox and use it for the tutorial.

##install Eclipse