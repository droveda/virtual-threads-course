package com.droveda.example.restexample;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;

public class HttpPlayer {

    private static final int NUM_USERS = 1;

    public static void main(String[] args) {

        ThreadFactory factory = Thread.ofVirtual().name("request-handler-", 0).factory();

        try (var srv = Executors.newThreadPerTaskExecutor(factory)) {

            IntStream.range(0, NUM_USERS).forEach(i -> {
                srv.submit(new UserRequestHandler());
            });

        }

    }

}
