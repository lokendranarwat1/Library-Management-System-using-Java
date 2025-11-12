package com.example.library.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "issued_books")
public class IssuedBook {
    @Id
    private String id;
    private String bookId;
    private String studentId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate issueDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    private String campus;

    public IssuedBook() {}

    public IssuedBook(String id, String bookId, String studentId, LocalDate issueDate, LocalDate returnDate, String campus) {
        this.id = id;
        this.bookId = bookId;
        this.studentId = studentId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.campus = campus;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }

    @Override
    public String toString() {
        return "IssuedBook{" + "id='" + id + '\'' + ", bookId='" + bookId + '\'' +
                ", studentId='" + studentId + '\'' + ", issueDate=" + issueDate +
                ", returnDate=" + returnDate + ", campus='" + campus + '\'' + '}';
    }
}
