package com.example.library.services;

import com.example.library.entities.Book;
import com.example.library.exceptions.CustomEmptyDataException;
import com.example.library.repositories.BookRepository;
import com.example.library.services.interfaces.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public Book createBook(Book book) {
        Optional<Book> bookForCreateOptional = bookRepository.findById(book.getId());
        if (bookForCreateOptional.isEmpty()) {
            book.setFree(checkIsFree(book));
            bookRepository.save(book);

            return book;
        } else {
            throw new IllegalArgumentException("Book is already create");
        }
    }

    @Override
    @Transactional
    public Book getBook(Long id) {
        return bookRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Book updateBook(Book book, Long id) {
        Optional<Book> bookForUpdateOptional = bookRepository.findById(id);
        if (bookForUpdateOptional.isPresent()) {
            Book target = bookForUpdateOptional.get();
            target.setName(book.getName());
            target.setFree(checkIsFree(book));
            if(target.getUser() == null){
                target.setUser(book.getUser());
            }

            bookRepository.save(target);

            return target;
        } else {
            throw new CustomEmptyDataException("Unable to update book");
        }
    }

    @Override
    @Transactional
    public String deleteBook(Long id) {
        Optional<Book> bookForDeleteOptional = bookRepository.findById(id);
        if (bookForDeleteOptional.isPresent()) {
            bookRepository.delete(bookForDeleteOptional.get());

            return "Book with id:" + id + " was successfully removed";
        } else {
            throw new CustomEmptyDataException("Unable to delete book");
        }
    }

    @Override
    @Transactional
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public boolean checkIsFree(Book book) {
        return book.getUser() == null;
    }

    @Override
    public String releaseBook(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent() && !bookOptional.get().isFree()) {
            Book releasedBook = bookOptional.get();
            releasedBook.setUser(null);
            releasedBook.setFree(true);
            bookRepository.save(releasedBook);

            return "Book with id:" + bookId + " is free";
        } else {
            throw new CustomEmptyDataException("Unable to release book");
        }
    }
}
