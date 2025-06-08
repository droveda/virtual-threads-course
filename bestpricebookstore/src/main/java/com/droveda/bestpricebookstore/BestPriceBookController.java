package com.droveda.bestpricebookstore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;

@RestController
@RequestMapping("/virtualstore")
public class BestPriceBookController {

    public static final ScopedValue<RestCallStatistics> TIMEMAP = ScopedValue.newInstance();

    private final BookRetrievalService service;

    public BestPriceBookController(BookRetrievalService service) {
        this.service = service;
    }

    @GetMapping("/book")
    public BestPriceResult getBestPriceForBook(@RequestParam String name) {

        long start = System.currentTimeMillis();

        RestCallStatistics timeObj = new RestCallStatistics();
        try {
            var books = ScopedValue.callWhere(TIMEMAP, timeObj, () -> service.getBookFromAllStores(name));

            Book book = books.stream()
                    .min(Comparator.comparing(Book::cost))
                    .orElseThrow();

            return new BestPriceResult(timeObj, book, books);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            long end = System.currentTimeMillis();
            timeObj.addTiming("Best Price Store", end - start);

            timeObj.dumpTiming();
        }
    }


}
