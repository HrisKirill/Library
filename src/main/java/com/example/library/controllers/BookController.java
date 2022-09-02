package com.example.library.controllers;

import com.example.library.entities.Book;
import com.example.library.entities.User;
import com.example.library.services.BookService;
import com.example.library.services.interfaces.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final IBookService bookService;

    @Autowired
    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book result = bookService.getBook(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> result = bookService.getAllBooks();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book source) {
        Book result = bookService.updateBook(source, id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/release/{id}")
    public ResponseEntity<String> releaseBook(@PathVariable Long id) {;
        return new ResponseEntity<>(bookService.releaseBook(id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.deleteBook(id), HttpStatus.OK);
    }
}
