package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.IssuedBook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CentralService {
    private final MongoTemplate north;
    private final MongoTemplate south;
    private final MongoTemplate central;

    public CentralService(@Qualifier("northMongoTemplate") MongoTemplate north,
                          @Qualifier("southMongoTemplate") MongoTemplate south,
                          @Qualifier("centralMongoTemplate") MongoTemplate central) {
        this.north = north;
        this.south = south;
        this.central = central;
    }

    public List<Book> getAllBooksAcrossCampuses() {
        List<Book> out = new ArrayList<>();
        out.addAll(north.findAll(Book.class));
        out.addAll(south.findAll(Book.class));
        return out;
    }

    public List<IssuedBook> getAllUnreturnedAcross() {
        Query q = Query.query(Criteria.where("returnDate").is(null));
        List<IssuedBook> out = new ArrayList<>();
        out.addAll(north.find(q, IssuedBook.class));
        out.addAll(south.find(q, IssuedBook.class));
        return out;
    }

    /**
     * Example sync: removes central 'books' collection and writes fresh copy.
     * Use with care in production.
     */
    public void syncBooksToCentral() {
        List<Book> all = getAllBooksAcrossCampuses();
        central.getCollection("books").drop();
        for (Book b : all) {
            central.save(b);
        }
    }

    public long countBooksInCentral() {
        return central.getCollection("books").countDocuments();
    }
}
