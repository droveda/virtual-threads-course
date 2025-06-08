package com.droveda.example.structuredconcurrency;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainLongRunning {

    public static void main(String[] args) throws Exception {

        System.out.println("Main started");

        LongRunningTask task = new LongRunningTask("LongTask1", 10, "json-response1", false);

        try (var service = Executors.newFixedThreadPool(2)) {
            Future<TaskResponse> taskFuture = service.submit(task);

            Thread.sleep(Duration.ofSeconds(5));
            taskFuture.cancel(true);
        }

        System.out.println("Main completed");

    }

}
