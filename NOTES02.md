## Completable Future

Java Completeble Future is equivalente to JavaScript promisses.  
Java Completable Future was introduced in Java 8, and allowed reactive programming in Java.  

It executes a task in the **Common ForkJoin Pool** of the JVM by default.  
Both runAsync and supplyAsync has an overloaded method where another executor service can be passed as an additional parameter.  

Note: differentce between the pipeline creation stage Versus the pipeline execution stage  

See example:  
com.droveda.example.completablefuture.CompletableFutureTest  

```
public class CompletableFuture<T> implements Future<T>, CompletionStage<T> {

    //methods to start a new task on a new thread. Overloaded methods available to use Executor
    public static CompletableFuture<Void> runAsync(Runnable runnable)
    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)

    //methods to help with the pipeline. Overloaded methods available to use Executor
    thenApply(Function<? super T,? extends U> fn)
    thenCompose(Function<? super T, ? extends CompletionStage<U>> fn)
    thenAccept(Consumer<? super T> action)
    thenRun(Runnable action)

    //combine results of two tasks
    public <U,V> CompletableFuture<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn)

    //Handle multiple Completable Futures
    public static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs)
    public static CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs)

    //Complete a CompletableFuture
    public boolean complete(T value)
    public boolean completeExceptionally(Throwable ex)

    //Methods to avoid because they block
    public T get() throws InterruptedException, ExecutionException
    public T join()

}
```


```
    public static void main(String[] args) {

         //execute a task in common pool
        //then apply a function
        //then accept the result which will be consumed by Consumer
        CompletableFuture<Void> pipeline = CompletableFuture.supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return new TaskResult("task1", 3);
                })
                .exceptionally(t -> new TaskResult("someTask", 1))
                .thenApply(TaskResult::getSecs)
                .thenApply(time -> time * 1000)
                .thenAccept(System.out::println);

    }
```
Now undestand the subtle difference between pipeline creation versus pipeline execution.  


### Some tidbits
whenComplete  

```
CompletableFuture pipeline =
    CompletableFuture
        .allOf(future1, future2)
        .whenComplete((unused, throwable) -> {
            if (throwable == null) {
                sout(future1.join, future2.join);
            } else {
                handErrors(throwable);
            }
        })
```

orTimeout  
```
Supplier<TaskResult> task1 = () -> FuturesPlay.doTask("task1", 3, false);
CompletableFuture pipeline = CompletableFuture.supplyAsync(task1)
    .orTimeout(1, TimeUnit.SECONDS)
    .thenAccept(System.out::println);

```


Nice site to test http calls:  
https://httpbin.org/delay/10  