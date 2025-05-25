package com.droveda.example.restexample;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class UserRequestHandler implements Callable<String> {

    @Override
    public String call() throws Exception {
//        return sequentialCall();

//        return concurrentCallWithFutures();

//        return concurrentCallFunctional();

//        return completableFuture();

        return completableFutureAssignment();
    }

    private String completableFutureAssignment() {
        try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {

            String out = CompletableFuture.supplyAsync(this::dbCall1, service)
                    .thenApplyAsync(data -> {
                        return data + " | " + CompletableFuture.supplyAsync(this::dbCall2, service)
                                .join();
                    }, service)
                    .thenCombine(CompletableFuture.supplyAsync(this::restCall1, service), (result1, result2) -> {
                        return result1 + " : " + result2;
                    })
                    .thenCombine(CompletableFuture.supplyAsync(this::restCall2, service), (result1, result2) -> {
                        return result1 + " : " + result2;
                    })
                    .join();

            System.out.println(out);
            return out;
        }
    }

    private String completableFuture() throws InterruptedException {
        try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {

            String output = CompletableFuture.supplyAsync(this::dbCall1, service)
                    .thenCombine(CompletableFuture.supplyAsync(this::restCall1, service), (result1, result2) -> {
                        return result1 + " : " + result2;
                    }).join();

            System.out.println(output);
            return output;
        }
    }

    private String concurrentCallFunctional() throws InterruptedException {
        try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {

            String result = service.invokeAll(Arrays.asList(this::dbCall1, this::restCall1))
                    .stream()
                    .map(f -> {
                        try {
                            return (String) f.get();
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .collect(Collectors.joining(","));

            return "[" + result + "]";
        }
    }

    private String concurrentCallWithFutures() throws InterruptedException, ExecutionException {
        try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {

            Future<String> dbFuture = service.submit(this::dbCall1);
            Future<String> restFuture = service.submit(this::restCall1);

            long start = System.currentTimeMillis();
            String result = dbFuture.get() + " : " + restFuture.get();
            long end = System.currentTimeMillis();
            System.out.println("time = " + (end - start));
            System.out.println(result);

            return result;
        }
    }

    private String sequentialCall() {

        long start = System.currentTimeMillis();
        //sequential coding
        String result1 = dbCall1(); // 2 seconds
        String result2 = restCall1(); // 5 seconds

        long end = System.currentTimeMillis();
        System.out.println("time = " + (end - start));

        String result = result1 + " : " + result2;
        System.out.println(result);


        return result;
    }

    private String dbCall1() {

        try {
            NetworkCaller caller = new NetworkCaller("data");
            return caller.makeCall(2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private String dbCall2() {

        try {
            NetworkCaller caller = new NetworkCaller("data2");
            return caller.makeCall(3);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private String restCall1() {
        try {
            NetworkCaller caller = new NetworkCaller("rest");

            return caller.makeCall(4);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String restCall2() {
        try {
            NetworkCaller caller = new NetworkCaller("rest2");

            return caller.makeCall(5);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
