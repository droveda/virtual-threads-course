# Structured Concurrency

## JDK Classes
* StructuredTaskScope
* Subtask


## Thread Cancellation
* Methods is Thread Class
  * public void interrupt() //sets the interrupted status of a thread to TRUE
  * public static boolean interrupted() //checks the interrupted status flag and if TRUE - clears it
  * public boolean isInterrupted() // checks the interrupted status of the flag but does not clear it
* Cooperative mechanism
* Both Platform Threads and Virtual Threads

### Thread Cancellation process
* Interruptor must call interrupt() to set the flag
* Interrupted Thread must
  * May choose to ignore the interrupt
  * Check **interrupted** status periodically
  * JDK methods like wait(), sleep(), join() will check status automatically
    * Throws **InterruptedException**
    * Clears the **interrupted** status flag
* Futures
```
//Sends an interrupt to the Child thread
Future<TaskResponse> taskFuture = exec.submit(callable);
taskFuture.cancel(true);
```


## Structured Concurrency Java Classes
* StructuredTaskScope
* Subtask

Use Cases  
* Shutdown when all Child threads complete
  * Example -> Request Airfare prices from different travel sites
* Shutdown when first child Thread fails
  * Example -> Split an Enterprise use case into smaller parts and combine
* Shutdown when first Child Thread succeeds
  * Example -> Request Weather information from multiples sites but choose first one
* Custom