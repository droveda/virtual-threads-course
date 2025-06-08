package com.droveda.example.structuredconcurrency;

import java.time.Duration;
import java.util.concurrent.Callable;

public class LongRunningTask implements Callable<TaskResponse> {

    private final String name;
    private final int time;
    private final String output;
    private final boolean fail;


    public LongRunningTask(String name, int time, String output, boolean fail) {
        this.name = name;
        this.time = time;
        this.output = output;
        this.fail = fail;
    }

    @Override
    public TaskResponse call() throws Exception {
        long start = System.currentTimeMillis();

        print("Started");

        int numSecs = 0;
        while (numSecs++ < time) {
            if (Thread.interrupted()) {
                throwInterruptedException();
            }

            print("Working ..." + numSecs);
            // process data which uses PCU for 0.2 secs

            try {
                Thread.sleep(Duration.ofSeconds(1));
                // new NetworkCaller(name).makeCall(1);
            } catch (InterruptedException ex) {
                throwInterruptedException();
            }

            // process data which uses CPU for 0.2 secs
        }

        if (fail) {
            throwExceptionOnFailure();
        }

        print("Completed");
        long end = System.currentTimeMillis();
        return new TaskResponse(this.name, this.output, (end - start));
    }

    private void throwExceptionOnFailure() {
        print("Failed");
        throw new RuntimeException(name + " : Failed");
    }

    private void throwInterruptedException() throws InterruptedException {
        print("Interrupted");
        throw new InterruptedException(name + " : Interrupted");
    }

    private void print(String message) {
        System.out.printf("> %s : %s\n", name, message);
    }


}
