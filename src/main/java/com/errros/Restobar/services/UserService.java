package com.errros.Restobar.services;


import com.errros.Restobar.entities.User;
import com.errros.Restobar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }






}
