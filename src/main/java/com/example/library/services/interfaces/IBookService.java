package com.example.library.services.interfaces;

import com.example.library.entities.Book;

import java.util.List;

public interface IBookService {

    Book createBook(Book book);

    Book getBook(Long id);

    Book updateBook(Book book, Long id);

    String deleteBook(Long id);

    List<Book> getAllBooks();

    String releaseBook(Long bookId);

    boolean checkIsFree(Book book);
}
