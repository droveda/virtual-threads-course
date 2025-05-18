package com.droveda.example.completablefuture;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;

public class ReadFileAsync {

    public static void main(String[] args) throws IOException {
        readFileAsync("some-file.txt")
                .thenAccept(content -> System.out.println(content));
    }

    private static CompletableFuture<String> readFileAsync(String fileName) throws IOException {
        CompletableFuture<String> future = new CompletableFuture<>();

        Path path = Paths.get("src/main/resources", fileName);
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate((int) path.toFile().length());

        fileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {

                attachment.flip();
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                attachment.clear();

                //complete successfully
                future.complete(new String(data));
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                //complete exceptionally
                future.completeExceptionally(exc);
            }
        });

        return future;
    }

}
