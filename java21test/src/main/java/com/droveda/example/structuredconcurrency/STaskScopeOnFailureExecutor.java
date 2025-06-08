package com.droveda.example.structuredconcurrency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.StructuredTaskScope;

public class STaskScopeOnFailureExecutor {

    public static Map<String, TaskResponse> execute(List<LongRunningTask> tasks) throws Throwable {
        try (var scope = new StructuredTaskScope<TaskResponse>()) {
            Map<String, TaskResponse> responses = new HashMap<>();

            List<StructuredTaskScope.Subtask<TaskResponse>> subtasks = new ArrayList<>();
            for (LongRunningTask task : tasks) {
                StructuredTaskScope.Subtask<TaskResponse> subTask = scope.fork(task);
                subtasks.add(subTask);
            }

            scope.join();


            for (StructuredTaskScope.Subtask<TaskResponse> subtask : subtasks) {
                if (StructuredTaskScope.Subtask.State.SUCCESS == subtask.state()) {
                    TaskResponse taskResponse = subtask.get();
                    responses.put(taskResponse.name(), taskResponse);
                } else if (StructuredTaskScope.Subtask.State.FAILED == subtask.state()) {
                    throw subtask.exception();
                }
            }


            return responses;
        }
    }

    public static void main(String[] args) throws Throwable {

        var dbTask = new LongRunningTask("dataTask", 3, "row1", false);
        var restTask = new LongRunningTask("restTask", 10, "json2", false);
        var extTask = new LongRunningTask("extTask", 5, "json3", false);

        Map<String, TaskResponse> result = execute(List.of(dbTask, restTask, extTask));

        result.forEach((k, v) -> {
            System.out.printf("%s : %s\n", k, v);
        });

    }

}
