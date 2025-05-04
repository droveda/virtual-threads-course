package com.droveda.example;

import java.util.concurrent.TimeUnit;

public class MyApp {

    public static void main(String[] args) {
        System.out.println("Hello Java21 here!");

        var thread = new SimpleThread("Simple Thread", 4);
        //thread.setDaemon(true);
        thread.start();

        System.out.println("Main method finished");

        Thread thread1 = new Thread(new SimpleRunnable());
        thread1.start();

        Thread.ofPlatform().name("Simple").daemon(true).start(() -> {
            System.out.println("Daemon thread");
        });

        Thread.ofPlatform().start(() -> {
            try {
                System.out.println("Running 2");
                TimeUnit.SECONDS.sleep(7);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Ending 2");
        });
    }

}
