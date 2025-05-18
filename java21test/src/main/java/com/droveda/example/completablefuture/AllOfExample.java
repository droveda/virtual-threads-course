package com.droveda.example.completablefuture;

import com.droveda.example.future.TaskResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class AllOfExample {


    public static void main(String[] args) {

        Supplier<TaskResult> task1 = () -> {
            System.out.println("Task1 started");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task1 completed");
            return new TaskResult("Task1", 3);
        };

        Supplier<TaskResult> task2 = () -> {
            System.out.println("Task2 started");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task2 completed");
            return new TaskResult("Task2", 2);
        };

        Supplier<TaskResult> task3 = () -> {
            System.out.println("Task3 started");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task3 completed");
            return new TaskResult("Task3", 5);
        };

        CompletableFuture<Void> futureAllOf = CompletableFuture.allOf(
                CompletableFuture.supplyAsync(task1),
                CompletableFuture.supplyAsync(task2),
                CompletableFuture.supplyAsync(task3)
        );

        CompletableFuture<Void> allTasksCompleted = futureAllOf.thenAccept(unused -> {
                    System.out.println("All tasks completed");
                })
                .exceptionally(throwable -> {
                    System.out.println("An error occurred: " + throwable.getMessage());
                    return null;
                });

        allTasksCompleted.join();

    }

}
