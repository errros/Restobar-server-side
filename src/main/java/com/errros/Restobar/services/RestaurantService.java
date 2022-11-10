package com.errros.Restobar.services;


import com.errros.Restobar.entities.Cashier;
import com.errros.Restobar.entities.Owner;
import com.errros.Restobar.entities.Restaurant;
import com.errros.Restobar.entities.User;
import com.errros.Restobar.models.RestaurantRequest;
import com.errros.Restobar.models.UserRequest;
import com.errros.Restobar.models.UserRole;
import com.errros.Restobar.repositories.*;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private CashierRepository cashierRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private Sys_AdminRepository sys_adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public Restaurant createRestaurant(RestaurantRequest restaurantRequest) {

        var sysadmin = sys_adminRepository.findAll().get(0);
        Restaurant restaurant = new Restaurant(restaurantRequest);
        restaurant.addSys_admin(sysadmin);
        return restaurantRepository.save(restaurant);

    }

    public void deleteRestaurant(Long id) {
     restaurantRepository.deleteById(id);
    }

    public Restaurant updateRestaurant(Long id, RestaurantRequest restaurantRequest) {


        //this will throw an error if a restaurant with such an id doesnt exist
        var persistedRestaurant = restaurantRepository.getById(id);

        persistedRestaurant.setName(restaurantRequest.getName());
       persistedRestaurant.setPhoneNumber(restaurantRequest.getPhoneNumber());
       persistedRestaurant.setAddress(restaurantRequest.getAddress());


      return restaurantRepository.save(persistedRestaurant);
    }

    public Restaurant addCashier(Long id, Long iduser, UserRequest cashierRequest) {
        Cashier cashier = new Cashier(cashierRequest);
        cashier.setRole(UserRole.OWNER);
        Restaurant restaurant = restaurantRepository.getById(id);
        User user = userRepository.getById(iduser);

        if(user.getRole() == UserRole.SYS_ADMIN || restaurant.getOwners().contains(user)) {

            cashier.setPassword(passwordEncoder.encode(cashier.getPassword()));
            restaurant.addCashier(cashier);
           return restaurantRepository.save(restaurant);

        }else {
            throw new OpenApiResourceNotFoundException("Unauthorized Operation for such a user!");
        }


    }

    public void deleteOwner(Long id, Long idOwner, Owner owner) {

    }

    public void deleteCashier(Long id, Long idCashier, Cashier cashier) {

    }

    public Restaurant addOwner(Long id, Long iduser, UserRequest ownerRequest) {
        Owner owner = new Owner(ownerRequest);

        owner.setRole(UserRole.OWNER);
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));

        Restaurant restaurant = restaurantRepository.getById(id);
        User user = userRepository.getById(iduser);

       if(user.getRole() == UserRole.SYS_ADMIN || restaurant.getOwners().contains(user)) {

           restaurant.addOwner(owner);
           return restaurantRepository.save(restaurant);

       }else {
           throw new OpenApiResourceNotFoundException("Unauthorized Operation for such a user!");
       }
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> getRestaurant(Long id) {
      return restaurantRepository.findById(id);
    }
}
