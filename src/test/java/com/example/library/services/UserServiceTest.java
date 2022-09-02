package com.example.library.services;

import com.example.library.LibraryApplication;
import com.example.library.entities.Book;
import com.example.library.entities.User;
import com.example.library.services.interfaces.IUserService;
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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private IUserService service;

    @Test
    @Order(1)
    @Rollback(value = false)
    void testCreateUser() {
       User user = User.builder()
               .id(1L)
               .name("Kirill")
               .build();

        service.createUser(user);

        Assertions.assertThat(service.getUser(1L).getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    void testGetUser() {
        User user = service.getUser(1L);
        Assertions.assertThat(user.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    void testUpdateUser() {
        service.updateUser(new User(1L,"Andrew"),1L);
        Assertions.assertThat(service.getUser(1L).getName()).isEqualTo("Andrew");
    }

    @Test
    @Order(6)
    void testDeleteUser() {
        service.deleteUser(1L);
        Assertions.assertThat(service.getAllUsers().size()).isEqualTo(0);
    }

    @Test
    @Order(4)
    void testGetAllUsers() {
        Assertions.assertThat(service.getAllUsers().size()).isGreaterThan(0);
    }

    @Test()
    @Order(5)
    void testFllBooksOfUser() {
        Assertions.assertThat(service.allBooksOfUser(1L).size()).isEqualTo(0);
    }
}