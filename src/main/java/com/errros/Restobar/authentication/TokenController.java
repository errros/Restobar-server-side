package com.errros.Restobar.authentication;


import com.errros.Restobar.authentication.jwt.JWTUtility;
import com.errros.Restobar.authentication.jwt.JwtRequestModel;
import com.errros.Restobar.authentication.jwt.JwtResponseModel;
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
    private ApplicationUserService applicationUserService;

    @Autowired
    private UserService userService;



    @PostMapping(path = "/token")
    JwtResponseModel authenticate(@RequestBody  JwtRequestModel request) throws Exception {

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
                = applicationUserService.loadUserByUsername(request.getEmail());


        final String token =
                jwtUtility.generateToken(userDetails);

        return  new JwtResponseModel(token,userService.findByUsername(userDetails.getUsername()).get());
    }

    @GetMapping("/")
    String hello(){
        return "Hello!";
    }

}
