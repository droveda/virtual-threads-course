package com.droveda.example.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableMainTest1 {

    public static void main(String[] args) {
        try (ExecutorService service = Executors.newFixedThreadPool(3)) {

            Future<TaskResult> future = service.submit(new CallablePlay("Task1", 2, false));
            Future<TaskResult> future2 = service.submit(new CallablePlay("Task2", 5, false));
            Future<TaskResult> future3 = service.submit(new CallablePlay("Task3", 1, false));

            try {
                TaskResult result = future.get();
                //TaskResult result2 = future2.get();
                System.out.println(result);
                //System.out.println(result2);

            } catch (Exception ex) {
                System.out.println(ex);
            }

        }
    }

}
