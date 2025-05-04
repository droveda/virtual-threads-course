# Virtual Theads Course

### Java Futures and Competable Futures Tutorial (Platform Threads)

* Extends Thread Class
* Implement Runnable Interface


#### Some notes:  
* By default platform threads are non deamon threads, unless you explicitly mark the thread as daemon thread.  
  * If there are any non deaemon threads running, the JVM will not shut down even if the main thread has terminated.
* Interface runnable the method run does not take any inout parameter and returns void, also it cannot throw a checked exception, it can throw an unchecked exception at any point


### Some useful method when working with threads
Get Current Thread  
```
Thread thread = Thread.currentThread();
System.out.println(thread.getName());
```

Interrupt another Thread  
```
thread.interrupt();
boolean interrupted = thread.isInterrupted();
```

Join  
```
Thread thread = Thread.ofPlatform().start(ThreadPlay::doSomething);
thread.join();
// once you start a thread from the current thread, you may want to join with the new thread again at a later point in time.
// join makes the current thread wait till the new thread terminates
// so in the example the thread join will wait till the thread has completed and fully terminated
// in most cases you would actually be doing other operations before you join back on the started thread, basically giving you some asynchronous behaviour.
```

sleep  
```
Thread.sleep(Duration.ofSeconds(5));
```

Set Daemon status
```
thread.setDaemon(true);
// for example, you would not mark a user request thread in an application server as a daemon thread,
// but you may mark a thread which periodically does some operations in the background to be a daemon thread. Because it is running in the background.
// If there are only daemon threads running, the JVM will decide to shut itself down.
```