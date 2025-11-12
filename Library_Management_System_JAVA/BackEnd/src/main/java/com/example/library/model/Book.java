package com.example.library.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String publisher;
    private String campus;

    public Book() {}

    public Book(String id, String title, String author, String publisher, String campus) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.campus = campus;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }

    @Override
    public String toString() {
        return "Book{" + "id='" + id + '\'' + ", title='" + title + '\'' +
                ", author='" + author + '\'' + ", publisher='" + publisher + '\'' +
                ", campus='" + campus + '\'' + '}';
    }
}
