package com.errros.Restobar.controllers;


import com.errros.Restobar.config.authentication.UserDetailsImpl;
import com.errros.Restobar.entities.Cashier;
import com.errros.Restobar.entities.Owner;
import com.errros.Restobar.entities.Restaurant;
import com.errros.Restobar.entities.User;
import com.errros.Restobar.models.UserRole;
import com.errros.Restobar.repositories.CashierRepository;
import com.errros.Restobar.repositories.OwnerRepository;
import com.errros.Restobar.repositories.RestaurantRepository;
import com.errros.Restobar.services.RestaurantService;
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

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CashierRepository cashierRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;


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

        Restaurant restaurant = null;
        User user = userService.findByEmail(userDetails.getUsername()).get();
         if(user.getRole().equals(UserRole.OWNER)){
             Owner owner = ownerRepository.getById(user.getId());
             restaurant = owner.getRestaurant();

         }else if (user.getRole().equals(UserRole.CASHIER)) {
             Cashier cashier = cashierRepository.getById(user.getId());
             restaurant = cashier.getRestaurant();
         }


        //String restaurant = userDetails.getAuthorities().contains(UserRole.OWNER) ? restaurantRepository.getByOwner()

        return  new JwtResponseModel(token,user,restaurant);


    }

    @GetMapping("/")
    String hello(){
        return "Hello!";
    }

}
