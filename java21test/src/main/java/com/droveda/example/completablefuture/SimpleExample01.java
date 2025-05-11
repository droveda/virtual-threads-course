package com.droveda.example.completablefuture;

import com.droveda.example.future.TaskResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SimpleExample01 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //Execute a task in the Common ForkJoin Pool of JVM
        CompletableFuture<Void> taskFuture = CompletableFuture.runAsync(() ->
                {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Something to run");
                }
        );


        CompletableFuture<TaskResult> taskFuture2 = CompletableFuture.supplyAsync(() ->
                {
                    try {
                        TimeUnit.SECONDS.sleep(4);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return new TaskResult("Task1", 2);
                }
        );

        taskFuture.get();
        TaskResult result = taskFuture2.get();
        System.out.println(result);

    }

}
