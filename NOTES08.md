# Spring Boot and Virtual Threads

```
spring.application.name=bookstore
server.port=8080
logging.level.web=DEBUG
spring.threads.virtual.enabled=true
# Max socket connections that can be handled by tomcat
server.tomcat.max-connections=50000
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%15thread] %msg%n
```

For load testing will be using: **Apache Benchmarking Tool**  
command line for apache benchmark:  
1000 concurrent users ```ab -n 1000 -c 1000 -s 300 -r http://localhost:8080/virtualstore/book?name=The+poet```  

10000 concurrent users ```ab -n 10000 -c 10000 -s 300 -r http://localhost:8080/virtualstore/book?name=The+poet```  

20000 concurrent users ```ab -n 20000 -c 20000 -s 300 -r http://localhost:8080/virtualstore/book?name=The+poet```  


```
Extending Scalability Testing

The following Apache Benchmark (ab) command that can be entered (max concurrency of 20000)


ab -c 20000 -n 20000 -s 300 -r -l http://<host>:<port>/virtualstore/book?name=<name>


Use "ab -h" for help on the various options that can be used. 


Extending our journey of increasing the number of users for Best Price Store application, how far can we take it for a single instance of the application ? We would have to monitor the memory usage of the application and the CPU Utilization to make that decision.


The application was able to support 100,000 concurrent users with CPU Utilization of 40% with the setup as below. The Apache benchmark had to be run from two windows machines because only maximum of 60000 connections can be made from each.


Servers Setup (Mac and Linux)

Application Memory (-Xmx): 1G

Ulimit -n : 250000 (# of file descriptors)

server.tomcat.max-connections=100000


Testing Machines Setup

Number of Windows machines for testing : 2

Number of dynamic port setup : 58000

          netsh int ipv4 set dynamicport tcp 1025 58000



Mac Mini Specification (Best Price App):

Memory : 16G

Chip : Apple M2

CPU Cores :

Mac OS : Ventura 13.3.1

```