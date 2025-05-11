package com.droveda.example;

import java.util.concurrent.TimeUnit;

public class AnotherMainToTestJoin {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("This is my main thread start");

        Thread thread = Thread.ofPlatform().start(() -> {
            try {
                System.out.println("My new Thread running...");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("My new Thread ended...");
        });

        System.out.println("This is my main thread before the join");

        thread.join();

        System.out.println("This is my main thread after the join");
        System.out.println("Main thread - Ending my program...");
    }

}
