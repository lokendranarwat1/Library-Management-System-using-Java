package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.IssuedBook;
import com.example.library.model.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampusService {
    private final MongoTemplate north;
    private final MongoTemplate south;

    public CampusService(@Qualifier("northMongoTemplate") MongoTemplate north,
                         @Qualifier("southMongoTemplate") MongoTemplate south) {
        this.north = north;
        this.south = south;
    }

    public List<Book> getAllBooksFrom(String campus) {
        if ("North".equalsIgnoreCase(campus)) return north.findAll(Book.class);
        if ("South".equalsIgnoreCase(campus)) return south.findAll(Book.class);
        throw new IllegalArgumentException("Unknown campus: " + campus);
    }

    public List<IssuedBook> getUnreturnedFrom(String campus) {
        Query q = Query.query(Criteria.where("returnDate").is(null));
        if ("North".equalsIgnoreCase(campus)) return north.find(q, IssuedBook.class);
        if ("South".equalsIgnoreCase(campus)) return south.find(q, IssuedBook.class);
        throw new IllegalArgumentException("Unknown campus: " + campus);
    }

    public Book addBookToCampus(Book b) {
        if (b.getCampus() == null) throw new IllegalArgumentException("Campus missing on Book");
        if ("North".equalsIgnoreCase(b.getCampus())) { north.save(b); return b; }
        if ("South".equalsIgnoreCase(b.getCampus())) { south.save(b); return b; }
        throw new IllegalArgumentException("Unknown campus: " + b.getCampus());
    }

    public Student addStudentToCampus(Student s) {
        if (s.getCampus() == null) throw new IllegalArgumentException("Campus missing on Student");
        if ("North".equalsIgnoreCase(s.getCampus())) { north.save(s); return s; }
        if ("South".equalsIgnoreCase(s.getCampus())) { south.save(s); return s; }
        throw new IllegalArgumentException("Unknown campus: " + s.getCampus());
    }

    public IssuedBook issueBookToCampus(IssuedBook i) {
        if (i.getCampus() == null) throw new IllegalArgumentException("Campus missing on IssuedBook");
        if ("North".equalsIgnoreCase(i.getCampus())) { north.save(i); return i; }
        if ("South".equalsIgnoreCase(i.getCampus())) { south.save(i); return i; }
        throw new IllegalArgumentException("Unknown campus: " + i.getCampus());
    }
}
