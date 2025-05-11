package com.droveda.example.completablefuture;

import com.droveda.example.future.TaskResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class SimpleExample02 {

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

        pipeline.join();

    }


}
