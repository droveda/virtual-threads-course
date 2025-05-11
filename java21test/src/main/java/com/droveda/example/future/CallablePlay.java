package com.droveda.example.future;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CallablePlay implements Callable<TaskResult> {

    private final String name;
    private final int secs;
    private final boolean fail;

    public CallablePlay(String name, int secs, boolean fail) {
        this.name = name;
        this.secs = secs;
        this.fail = fail;
    }

    @Override
    public TaskResult call() throws Exception {
        return doTask(name, secs, fail);
    }

    private TaskResult doTask(String name, int secs, boolean fail) {

        var threadName = Thread.currentThread().getName();

        System.out.println(threadName + " - Starting Task " + name);

        try {
            TimeUnit.SECONDS.sleep(secs);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        if (fail) {
            throw new RuntimeException("Task " + name + " failed!");
        }

        System.out.println(threadName + " - Task " + name + " completed!");
        return new TaskResult(name, secs);
    }
}
