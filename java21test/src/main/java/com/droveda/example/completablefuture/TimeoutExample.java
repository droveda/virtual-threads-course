package com.droveda.example.completablefuture;

import com.droveda.example.future.TaskResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class TimeoutExample {

    public static void main(String[] args) {
        Supplier<TaskResult> task1 = () -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new TaskResult("Task1", 3);
        };
        CompletableFuture<Void> pipeline = CompletableFuture.supplyAsync(task1)
                .orTimeout(1, TimeUnit.SECONDS)
                .thenAccept(System.out::println);

        pipeline.join();
    }

}
