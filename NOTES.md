# Virtual Theads Course

### Java Futures and Competable Futures Tutorial (Platform Threads)

* Extends Thread Class
* Implement Runnable Interface


#### Some notes:  
* By default platform threads are non deamon threads, unless you explicitly mark the thread as daemon thread.  
  * If there are any non deaemon threads running, the JVM will not shut down even if the main thread has terminated.
* Interface runnable, the method run does not take any input parameter and returns void, also it cannot throw a checked exception, it can throw an unchecked exception at any point


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
// Once you start a thread from the current thread, you may want to join with the new thread again at a later point in time.
// join makes the current thread wait till the new thread terminates.
// So in the example the thread join will wait till the thread has completed and fully terminated
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

## Java Futures
* Thread Pools
  * Platform Thread is an expensive Resource
  * Starting a platform thread takes some time, which might lead to performance issues
  * The solution for the problems is to create a thread pool (Application server already does that)
    * Tomcat creates 200 threads dedicated to user requests by default
* Key Abstraction is a **Task**
  * Runnable
  * Callable
* Executor Service
  * Mostly backed by a Thread Pool
  * Separates Task from its execution Policy

```
@FunctionalInterface
public interface Runnable {
  void run();
}

@FunctionalInterface
public interface Callable<V> {
  V call() throws Exception;
}

public interface ExecutorService extends Executor, AutoCloseable {
  <T> Future<T> submit(Callable<T> task);
  Future<?> submit(Runnable task);

  void shutdown();
  List<Runnable> shutdownNow();
  default void close();
}

public interface Future<T> {
  V get() throws InterruptedException, ExecutionException;
  V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;

  default V resultNow();
  default Throwable exceptionNow();

  boolean cancel(boolean mayInterruptIfRunning);

  // RUNNING, SUCCESS, FAILED, CANCELLED
  default java.util.concurrent.Future.State state();
  boolean is Cancelled();
}
```

## Java Futures Limitatios
* Cannot create an Asynchronous Pipeline
* Cannot Complete a Future
* Limited Features

### Imperative Style - Pseudo Code (Blocking)
```
//pseudo code for handling User Request

//fetch some data from the DB
data1 = FetchDataFromDB(dbUrl)

//fetch some data from a Microservice 1
data2 = FetchDataFromService1(url1)

//process all data
combinedData = ProcessAndCombine(data1, data2)

//send data to the user
SendData(combinedData)
```
The downside here, though, is that the thread blocks until all of the methods have finished processing.  
Now why is that such a big problem in highly scabable applications?    
We cannot afford the threads to block because it affects scalabilitty  

To correct this problem, Reactive style programming has become quite popular in recent years.  
The main purpose of reactive style development is to avoid blocking in your code.  

### Reactive Stype - Pseudo Code
```
//Reactive Pseudo code for handling User Request
//The user thread does not blobk

Pipeline
  .Run(FetchDataFromDB(dbURL))
  .Run(FetchDataFromService1(url1))
  .Combine(dataResult, ServiceResult)
  .SendData(combinedData)


//Method exists before Database and Service operations are completed!  
```
The thread which handles the user requests simply specifies what needs to be done as part of the pipeline to fulfill the user request
and exists withoud waiting for the database or service request to complete.  
In other words, the sole purpose of the user request thread is simply to construct the pipeline.  
  
The individual parts of the pipeline would be running on separate threads and it would be the job of the pipeline manager, whoever that may be, to orchestrate these asynchronous parts and then send the combined result back to the user.  
