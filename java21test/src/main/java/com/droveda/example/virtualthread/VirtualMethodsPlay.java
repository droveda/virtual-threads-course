package com.droveda.example.virtualthread;

import com.droveda.example.util.MyUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class VirtualMethodsPlay {

    private static void handleUserRequest() {
        System.out.println("Starting thread " + Thread.currentThread());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Finishing Thread " + Thread.currentThread());

    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting main");

//        playWithVirtualBuilder();
//        playWithFactory();

//        playWithExecutorService();
        playWithExecutorServiceWithFactory();

        mySolution();

        System.out.println("Ending main");
    }

    private static void playWithVirtualBuilder() throws InterruptedException {
        Thread.Builder.OfVirtual builder = Thread.ofVirtual().name("userThread", 0);

        Thread t1 = builder.start(VirtualMethodsPlay::handleUserRequest);
        Thread t2 = builder.start(VirtualMethodsPlay::handleUserRequest);

        t1.join();
        t2.join();
    }

    private static void playWithFactory() throws InterruptedException {
        ThreadFactory factory = Thread.ofVirtual().name("userThread", 0).factory();

        Thread t1 = factory.newThread(VirtualMethodsPlay::handleUserRequest);
        Thread t2 = factory.newThread(VirtualMethodsPlay::handleUserRequest);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    private static void playWithExecutorService() {
        try (ExecutorService srv = Executors.newVirtualThreadPerTaskExecutor()) {

            //submit two tasks to the executor service
            srv.submit(VirtualMethodsPlay::handleUserRequest);
            srv.submit(VirtualMethodsPlay::handleUserRequest);

        }
    }

    private static void playWithExecutorServiceWithFactory() {
        ThreadFactory factory = Thread.ofVirtual().name("userThread", 0).factory();

        try (ExecutorService srv = Executors.newThreadPerTaskExecutor(factory)) {

            //submit two tasks to the executor service
            srv.submit(VirtualMethodsPlay::handleUserRequest);
            srv.submit(VirtualMethodsPlay::handleUserRequest);

        }
    }

    private static void mySolution() {

        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            executorService.submit(() -> {
                System.out.println("Running my task");

                MyUtils.sleepForSeconds(3);

                System.out.println("Finished my task");

                return "aaa";
            });
        }

    }

}
