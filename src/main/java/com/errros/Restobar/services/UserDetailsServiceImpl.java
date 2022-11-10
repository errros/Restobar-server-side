package com.errros.Restobar.services;


import com.errros.Restobar.config.authentication.UserDetailsImpl;
import com.errros.Restobar.entities.User;
import com.errros.Restobar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(username);


        if(user.isEmpty()){
            throw new UsernameNotFoundException(String.format("user %s doesn't exist",username));
        }else{
            return   new UserDetailsImpl(user.get());
        }

    }


    public UserDetails realLoadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);


        if(user.isEmpty()){
            throw new UsernameNotFoundException(String.format("user %s doesn't exist",username));
        }else{
          return   new UserDetailsImpl(user.get());
        }
    }

}
