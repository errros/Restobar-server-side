package com.errros.Restobar.controllers;


import com.errros.Restobar.services.UserDetailsServiceImpl;
import com.errros.Restobar.services.UserService;
import com.errros.Restobar.jwt.JWTUtility;
import com.errros.Restobar.jwt.JwtRequestModel;
import com.errros.Restobar.jwt.JwtResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class TokenController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/token")
    JwtResponseModel generateToken(@RequestBody  JwtRequestModel request) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        UserDetails userDetails
                = userDetailsServiceImpl.loadUserByUsername(request.getEmail());


        final String token =
                jwtUtility.generateToken(userDetails);

        return  new JwtResponseModel(token,userService.findByUsername(userDetails.getUsername()).get());


    }

    @GetMapping("/")
    String hello(){
        return "Hello!";
    }

}
