package com.droveda.example.virtualthread;

import java.util.ArrayList;
import java.util.List;

public class MainJacketLoom {

    private static void handleUserRequest() {
        System.out.println("Starting thread " + Thread.currentThread());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Finishing Thread " + Thread.currentThread());

    }

    // -Xms256m -Xmx256m
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting Main " + Thread.currentThread());

        List<Thread> threads = new ArrayList<>();
//        for (int i = 0; i < 200000; i++) {
        for (int i = 0; i < 10; i++) {
            threads.add(startThread());
        }

        // join on the threads
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Ending main " + Thread.currentThread());

    }

    private static Thread startThread() {
//        new Thread(MainJacketLoom::handleUserRequest).start();

        // a virtual thread is a daemon thread, so the program will not wait for it to finish
        return Thread.startVirtualThread(MainJacketLoom::handleUserRequest);
    }

}
