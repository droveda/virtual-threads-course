package com.droveda.example.completablefuture;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpExample {

    public static void main(String[] args) throws Exception {

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().GET()
                    .uri(new URI("https://httpbin.org/delay/10")).build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .whenComplete((r, throwable) -> {
                        if (throwable != null) {
                            if (r.statusCode() >= 400) {
                                throw new RuntimeException("Http request responded with error");
                            }
                        }
                    })
                    .thenApply(r -> r.body())
                    .thenAccept(value -> System.out.println("my response is : " + value));

        }

    }

}
