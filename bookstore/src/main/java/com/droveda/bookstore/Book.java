package com.droveda.bookstore;

public record Book(
        String bookStore,
        String bookName,
        String author,
        int cost,
        int numPages,
        String link
) {
}
