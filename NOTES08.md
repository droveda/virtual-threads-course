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
