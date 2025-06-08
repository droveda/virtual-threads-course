package com.droveda.example.structuredconcurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;

public class AverageWeatherTaskScope extends StructuredTaskScope<TaskResponse> {

    private final List<Subtask<? extends TaskResponse>> successSubsTasks = Collections.synchronizedList(new ArrayList());

    @Override
    protected void handleComplete(Subtask<? extends TaskResponse> subtask) {

        if (subtask.state() == Subtask.State.SUCCESS) {
            add(subtask);
        }

    }

    private void add(Subtask<? extends TaskResponse> subtask) {
        int numSuccessful = 0;
        synchronized (successSubsTasks) {
            successSubsTasks.add(subtask);
            numSuccessful = successSubsTasks.size();
        }

        if (numSuccessful == 2) {
            this.shutdown();
        }
    }

    public AverageWeatherTaskScope join() throws InterruptedException {
        super.join();
        return this;
    }

    public TaskResponse response() {
        super.ensureOwnerAndJoined();

        if (successSubsTasks.size() != 2) {
            throw new RuntimeException("Atleast two substasks must be successful!");
        }

        TaskResponse r1 = successSubsTasks.get(0).get();
        TaskResponse r2 = successSubsTasks.get(1).get();

        Integer temp1 = Integer.valueOf(r1.response());
        Integer temp2 = Integer.valueOf(r2.response());

        return new TaskResponse("Weather", "" + ((temp1 + temp2) / 2), (r1.timeTaken() + r2.timeTaken()) / 2);

    }

}
