package com.droveda.example.restexample;

import java.io.InputStream;
import java.net.URI;

public class NetworkCaller {

    private String callName;

    public NetworkCaller(String callName) {
        this.callName = callName;
    }

    public String makeCall(int secs) throws Exception {

        System.out.println(callName + " : Called : " + Thread.currentThread());

        try {
            URI uri = new URI("http://httpbin.org/delay/" + secs);

            try (InputStream stream = uri.toURL().openStream()) {
                return new String(stream.readAllBytes());
            }
        } finally {
            System.out.println(callName + " : END call : " + Thread.currentThread());
        }

    }
}
