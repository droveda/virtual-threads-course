package com.droveda.example;

import java.util.concurrent.TimeUnit;

public class AnotherMainToTestJoin {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("This is my main thread start");

        Thread thread = Thread.ofPlatform().start(() -> {
            try {
                System.out.println("Thread 1");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Thread 2");
        });

        System.out.println("This is my main thread ABC");

        thread.join();

        System.out.println("This is my main thread end");

    }

}
