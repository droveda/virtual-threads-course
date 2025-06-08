package com.droveda.example.structuredconcurrency;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.StructuredTaskScope;

public class StructuredExample {

    public static void main(String[] args) throws Exception {

        System.out.println("Main started");

        //interruptMain();
//        exampleCompleteAllTasks();

//        exampleShutdownOnFailure();

//        exampleShutdownOnSuccess();
        
        exampleCustomTaskScope();

        System.out.println("Main completed");
    }

    private static void interruptMain() {

        Thread mainThread = Thread.currentThread();

        Thread.ofPlatform().start(() -> {

            try {
                Thread.sleep(Duration.ofSeconds(2));
                mainThread.interrupt();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        });

    }

    private static void exampleCompleteAllTasks() throws Exception {

        try (var scope = new StructuredTaskScope<TaskResponse>()) {

            var exp = new LongRunningTask("expedia-task", 3, "100$", false);
            var hot = new LongRunningTask("hotwire-task", 10, "110$", false);

            StructuredTaskScope.Subtask<TaskResponse> expSubTask = scope.fork(exp);
            StructuredTaskScope.Subtask<TaskResponse> hotSubTask = scope.fork(hot);

            //wait for all tasks to complete (success of not)
//            scope.join();
            scope.joinUntil(Instant.now().plus(Duration.ofSeconds(2)));

            StructuredTaskScope.Subtask.State expState = expSubTask.state();
            if (expState == StructuredTaskScope.Subtask.State.SUCCESS) {
                TaskResponse expResponse = expSubTask.get();
                System.out.println("Expedia Task completed: " + expResponse);
            } else if (expState == StructuredTaskScope.Subtask.State.FAILED) {
                System.out.println(expSubTask.exception());
            }

            StructuredTaskScope.Subtask.State hotState = hotSubTask.state();
            if (hotState == StructuredTaskScope.Subtask.State.SUCCESS) {
                TaskResponse expResponse = hotSubTask.get();
                System.out.println("Hotwire Task completed: " + expResponse);
            } else if (expState == StructuredTaskScope.Subtask.State.FAILED) {
                System.out.println(hotSubTask.exception());
            }

        }

    }


    private static void exampleShutdownOnFailure() throws Exception {

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            var data = new LongRunningTask("data-task", 3, "row", true);
            var rest = new LongRunningTask("rest-task", 10, "json", false);

            StructuredTaskScope.Subtask<TaskResponse> dataSubTask = scope.fork(data);
            StructuredTaskScope.Subtask<TaskResponse> restSubTask = scope.fork(rest);

            //wait for all tasks to complete (success of not)
            scope.join();
            scope.throwIfFailed();

            System.out.println(dataSubTask.get());
            System.out.println(restSubTask.get());

        }

    }


    private static void exampleShutdownOnSuccess() throws Exception {

        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<TaskResponse>()) {

            var weather1 = new LongRunningTask("weather-1-task", 3, "32", false);
            var weather2 = new LongRunningTask("weather-2-task", 10, "30", false);

            StructuredTaskScope.Subtask<TaskResponse> weather1SubTask = scope.fork(weather1);
            StructuredTaskScope.Subtask<TaskResponse> weather2SubTask = scope.fork(weather2);

            //wait for all tasks to complete (success of not)
            scope.join();

            TaskResponse result = scope.result();
            System.out.println(result);

        }

    }

    private static void exampleCustomTaskScope() throws Exception {

        try (var scope = new AverageWeatherTaskScope()) {

            var weather1 = new LongRunningTask("weather-1-task", 3, "30", false);
            var weather2 = new LongRunningTask("weather-2-task", 4, "32", false);
            var weather3 = new LongRunningTask("weather-3-task", 5, "34", false);
            var weather4 = new LongRunningTask("weather-4-task", 6, "34", false);
            var weather5 = new LongRunningTask("weather-5-task", 9, "30", false);

            StructuredTaskScope.Subtask<TaskResponse> weather1SubTask = scope.fork(weather1);
            StructuredTaskScope.Subtask<TaskResponse> weather2SubTask = scope.fork(weather2);
            StructuredTaskScope.Subtask<TaskResponse> weather3SubTask = scope.fork(weather3);
            StructuredTaskScope.Subtask<TaskResponse> weather4SubTask = scope.fork(weather4);
            StructuredTaskScope.Subtask<TaskResponse> weather5SubTask = scope.fork(weather5);

            //wait for all tasks to complete (success of not)
            scope.join();

            TaskResponse result = scope.response();
            System.out.println(result);

        }

    }


}
