package com.droveda.example.completablefuture;

import com.droveda.example.future.TaskResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class CompletableFutureTest {

    public static void main(String[] args) {

        Supplier<TaskResult> task1 = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return new TaskResult("task1", 2);
        };

        Supplier<TaskResult> task2 = () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return new TaskResult("task2", 5);
        };

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(task1)
                .thenCombine(
                        CompletableFuture.supplyAsync(task2),
                        (result1, result2) -> "Combined result : " + result1.getName() + " and " + result2.getName()
                )
                .thenApply(data -> data + " ::: Handled Apply")
                .thenAccept(data -> {
                    System.out.println("Result -> " + data + " ::: Handled Accept");
                });

        try {
            voidCompletableFuture.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
