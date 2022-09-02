package com.example.library.services.interfaces;


import com.example.library.entities.Book;
import com.example.library.entities.User;

import java.util.List;

public interface IUserService {

    User createUser(User user);

    User getUser(Long id);

    User updateUser(User user, Long id);

    String deleteUser(Long id);

    List<User> getAllUsers();

    List<Book> allBooksOfUser(Long id);
}
