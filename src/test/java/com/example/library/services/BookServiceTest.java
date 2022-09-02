package com.example.library.services;

import com.example.library.LibraryApplication;
import com.example.library.entities.Book;
import com.example.library.exceptions.CustomEmptyDataException;
import com.example.library.services.interfaces.IBookService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookServiceTest {

    @Autowired
    private IBookService service;


    @Test
    @Order(1)
    @Rollback(value = false)
    void testCreateBook() {
        Book book = Book.builder()
                .id(1L)
                .name("Book1")
                .isFree(true)
                .build();
        service.createBook(book);

        Assertions.assertThat(service.getBook(1L).getId()).isGreaterThan(0);
    }

    @Test
    void testReleaseBook() {
        org.junit.jupiter.api.Assertions.assertThrows(CustomEmptyDataException.class, () -> service.releaseBook(1L));
    }

    @Test
    @Order(2)
    void testGetBook() {
        Book book = service.getBook(1L);
        Assertions.assertThat(book.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    void testGetAllBooks() {
        List<Book> books = service.getAllBooks();
        Assertions.assertThat(books.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    void testUpdateBook() {
        service.updateBook(Book.builder()
                .name("UpdatedBook")
                .isFree(true)
                .build(), 1L);

        Assertions.assertThat(service.getBook(1L).getName()).isEqualTo("UpdatedBook");
    }

    @Test
    @Order(5)
    void testDeleteBook() {
        service.deleteBook(1L);
        Assertions.assertThat(service.getAllBooks().size()).isEqualTo(0);
    }


    @Test
    void testCheckIsFree() {
        Book book = Book.builder()
                .id(1L)
                .name("Book1")
                .build();
        Assertions.assertThat(service.checkIsFree(book)).isTrue();
    }


}