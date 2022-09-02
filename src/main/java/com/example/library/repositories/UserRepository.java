package com.example.library.repositories;

import com.example.library.entities.Book;
import com.example.library.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    List<User> findAll();

    @Query(value = "SELECT book FROM Book book WHERE book.user.id = :userId")
    List<Book> findAllBooksOfUser(@Param("userId") Long userId);
}
