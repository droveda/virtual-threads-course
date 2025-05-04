package com.droveda.example;

import java.util.concurrent.TimeUnit;

public class SimpleThread extends Thread {

    private final int secs;

    public SimpleThread(String name, int secs) {
        this.setName(name);
        this.secs = secs;
    }

    @Override
    public void run() {

        System.out.printf("%s : starting simple thread\n", this.getName());

        try {
            TimeUnit.SECONDS.sleep(this.secs);
        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
        }

        System.out.printf("%s : Ending simple thread\n", this.getName());

    }
}
