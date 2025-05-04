package com.droveda.example;

import java.util.concurrent.TimeUnit;

public class SimpleRunnable implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
        }

        System.out.println("Ending simple thread!!!");
    }
}
