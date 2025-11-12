package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.IssuedBook;
import com.example.library.model.Student;
import com.example.library.service.CampusService;
import com.example.library.service.CentralService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LibraryController {

    private final CampusService campusService;
    private final CentralService centralService;

    public LibraryController(CampusService campusService, CentralService centralService){
        this.campusService = campusService;
        this.centralService = centralService;
    }

    // Campus-level endpoints
    @PostMapping("/campus/book")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        return ResponseEntity.ok(campusService.addBookToCampus(book));
    }

    @PostMapping("/campus/student")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        return ResponseEntity.ok(campusService.addStudentToCampus(student));
    }

    @PostMapping("/campus/issue")
    public ResponseEntity<IssuedBook> issueBook(@RequestBody IssuedBook issued) {
        return ResponseEntity.ok(campusService.issueBookToCampus(issued));
    }

    @GetMapping("/campus/{campus}/books")
    public ResponseEntity<List<Book>> getCampusBooks(@PathVariable String campus) {
        return ResponseEntity.ok(campusService.getAllBooksFrom(campus));
    }

    @GetMapping("/campus/{campus}/unreturned")
    public ResponseEntity<List<IssuedBook>> getCampusUnreturned(@PathVariable String campus) {
        return ResponseEntity.ok(campusService.getUnreturnedFrom(campus));
    }

    // Central endpoints
    @GetMapping("/central/books")
    public ResponseEntity<List<Book>> allBooks() {
        return ResponseEntity.ok(centralService.getAllBooksAcrossCampuses());
    }

    @GetMapping("/central/unreturned")
    public ResponseEntity<List<IssuedBook>> unreturned() {
        return ResponseEntity.ok(centralService.getAllUnreturnedAcross());
    }

    @PostMapping("/central/sync-books")
    public ResponseEntity<String> syncBooksToCentral() {
        centralService.syncBooksToCentral();
        return ResponseEntity.ok("Synced books to central DB");
    }
}
