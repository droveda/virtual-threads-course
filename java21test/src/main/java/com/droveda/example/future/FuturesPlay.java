package com.droveda.example.future;

import java.util.concurrent.TimeUnit;

public class FuturesPlay implements Runnable {

    @Override
    public void run() {
        doSimpleTask();
    }

    private void doSimpleTask() {
        String thread = Thread.currentThread().getName();

        System.out.println(thread + " - Starting Simple Task");

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            System.out.println("Task Interrupted");
        }

        System.out.println(thread + " - Simple Task completed!");
    }
}
