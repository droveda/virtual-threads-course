package com.droveda.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/store")
public class BookstoreController {

    private static final Logger log = LoggerFactory.getLogger(BookstoreController.class);

    private final BookCollection bookCollection;

    public BookstoreController(BookCollection bookCollection) {
        this.bookCollection = bookCollection;
    }

    @GetMapping("/book")
    public Book getBookByName(@RequestParam String name) {
        log.info("Received request for book: {}, thread: {}", name, Thread.currentThread());

        delayOf5Secs();
        return bookCollection.findBook(name);
    }

    private void delayOf5Secs() {
        try {
            Thread.sleep(Duration.ofSeconds(5));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
