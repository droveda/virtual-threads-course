package com.droveda.example.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureMainTest1 {

    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
            Future<?> future = executorService.submit(new FuturesPlay());

            System.out.println("Before Get()");
            future.get();
            System.out.println("After Get() - Future completed");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Finish main");
    }

}
