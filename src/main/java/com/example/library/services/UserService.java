package com.example.library.services;


import com.example.library.entities.Book;
import com.example.library.entities.User;
import com.example.library.exceptions.CustomEmptyDataException;
import com.example.library.repositories.UserRepository;
import com.example.library.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        Optional<User> userForCreateOptional = repository.findById(user.getId());
        if (userForCreateOptional.isEmpty()) {
            repository.save(user);

            return user;
        } else {
            throw new IllegalArgumentException("User is already create");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return repository.findById(id).get();
    }

    @Override
    @Transactional
    public User updateUser(User user, Long id) {
        Optional<User> userForUpdateOptional = repository.findById(id);
        if (userForUpdateOptional.isPresent()) {
            User target = userForUpdateOptional.get();
            target.setName(user.getName());
            repository.save(target);
            return target;
        } else {
            throw new CustomEmptyDataException("Unable to update user");
        }
    }

    @Override
    @Transactional
    public String deleteUser(Long id) {
        Optional<User> userForDeleteOptional = repository.findById(id);
        if (userForDeleteOptional.isPresent()) {
            repository.delete(userForDeleteOptional.get());

            return "User with id:" + id + " was successfully removed";
        } else {
            throw new CustomEmptyDataException("Unable to delete user");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public List<Book> allBooksOfUser(Long id){
        return repository.findAllBooksOfUser(id);
    }
}
