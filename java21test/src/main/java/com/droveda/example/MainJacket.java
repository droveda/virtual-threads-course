package com.droveda.example;

public class MainJacket {

    private static void handleUserRequest() {
        System.out.println("Starting thread " + Thread.currentThread());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Finishing Thread " + Thread.currentThread());

    }

    // -Xms256m -Xmx256m
    public static void main(String[] args) {

        System.out.println("Starting Main");

        for (int i = 0; i < 100; i++) {
            new Thread(MainJacket::handleUserRequest).start();
        }

        System.out.println("Ending main");

    }

}
