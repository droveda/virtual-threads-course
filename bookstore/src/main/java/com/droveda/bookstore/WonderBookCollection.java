package com.droveda.bookstore;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("wonder")
public class WonderBookCollection implements BookCollection {

    @Value("${book.store.name}")
    private String storeName;

    private final List<Book> books = new ArrayList<>();

    @PostConstruct
    public void init() {
        books.add(
                new Book(
                        storeName, "And Then There Were None", "Agatha Christie", 10, 300,
                        bookUrl("And Then There Were None")));

        books.add(
                new Book(
                        storeName, "A Study in Scarlet", "Arthur Conan Doyle", 10, 108,
                        bookUrl("And Then There Were None")));

        books.add(
                new Book(
                        storeName, "The Day of the Jackal", "Frederick Forsyth", 11, 464,
                        bookUrl("The Day of the Jackal")));

        books.add(
                new Book(storeName, "The Wisdom of Father Brown", "G.K. Chesterton", 7, 136,
                        bookUrl("The Wisdom of Father Brown")));

        books.add(
                new Book(storeName, "The Poet", "Michael Connelly", 15, 528,
                        bookUrl("The Poet")));
    }

    @Override
    public Book findBook(String name) {
        return books.stream()
                .filter(b -> b.bookName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow();
    }

    private String bookUrl(String title) {
        try {
            return String.format(
                    "http://wonder:8081/store/book?name=%s",
                    URLEncoder.encode(title, "UTF-8"));
        } catch (Exception anyExp) {
            return "?";
        }
    }
}
