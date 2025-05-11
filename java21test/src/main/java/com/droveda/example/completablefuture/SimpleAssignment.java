package com.droveda.example.completablefuture;

import com.droveda.example.future.TaskResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class SimpleAssignment {

    public static void main(String[] args) {

        Supplier<TaskResult> task1 = () -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new TaskResult("Task1", 3);
        };

        Supplier<TaskResult> task2 = () -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new TaskResult("Task2", 4);
        };

        Supplier<TaskResult> task3 = () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new TaskResult("Task3", 4);
        };

        Supplier<TaskResult> task4 = () -> {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new TaskResult("Task4", 6);
        };

        CompletableFuture<TaskResult> future1 = CompletableFuture.supplyAsync(task1);
        CompletableFuture<TaskResult> future2 = CompletableFuture.supplyAsync(task2);
        //CompletableFuture<TaskResult> future3 = CompletableFuture.supplyAsync(task3);
        //CompletableFuture<TaskResult> future4 = CompletableFuture.supplyAsync(task4);

        CompletableFuture<Void> c = future1.thenCombine(future2, (r1, r2) -> {
                    //System.out.println("Combined result: " + r1.getName() + " and " + r2.getName());
                    return r1.getName() + r2.getName();
                })
                .thenApply(s -> s + " :: Glue :: ")
                .thenCompose(s -> {
                    var future3 = CompletableFuture.supplyAsync(task3);
                    var future4 = CompletableFuture.supplyAsync(task4);
                    return future3.thenCombine(future4, (r1, r2) -> s + r1.getName() + r2.getName());
                })
                .thenAccept(data -> {
                    System.out.println("Result -> " + data + " ::: Handled Accept");
                });

        c.join();

    }

}
