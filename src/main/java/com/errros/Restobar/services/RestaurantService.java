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




    public Restaurant addCashier(Long id, UserRequest cashierRequest) {
        Cashier cashier = new Cashier(cashierRequest);
        cashier.setRole(UserRole.CASHIER);
        cashier.setPassword(passwordEncoder.encode(cashier.getPassword()));

        Restaurant restaurant = restaurantRepository.getById(id);


            restaurant.addCashier(cashier);
           return restaurantRepository.save(restaurant);

        }




    public Restaurant addOwner(Long id, UserRequest ownerRequest) {
        Owner owner = new Owner(ownerRequest);
        owner.setRole(UserRole.OWNER);
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));

        Restaurant restaurant = restaurantRepository.getById(id);
           restaurant.addOwner(owner);

           return restaurantRepository.save(restaurant);

       }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public void deleteOwner(Long idRestaurant, Long idOwner) {
    var restaurant = restaurantRepository.getById(idRestaurant);
    Owner owner = ownerRepository.getById(idOwner);
        restaurant.removeOwner(owner);
        restaurantRepository.save(restaurant);
    }


    public void deleteCashier(Long idRestaurant, Long idCashier) {

            var restaurant = restaurantRepository.getById(idRestaurant);
            var cashier = cashierRepository.getById(idCashier);
            restaurant.removeCashier(cashier);
            restaurantRepository.save(restaurant);
    }

    public Restaurant getRestaurant(Long idRestaurant) {
                      Restaurant restaurant = restaurantRepository.getById(idRestaurant);
      return restaurant;
    }

    public Restaurant updateRestaurant(Long idRestaurant, RestaurantRequest restaurantRequest) {

        var persistedRestaurant = restaurantRepository.getById(idRestaurant);

        persistedRestaurant.setName(restaurantRequest.getName());
        persistedRestaurant.setPhoneNumber(restaurantRequest.getPhoneNumber());
        persistedRestaurant.setAddress(restaurantRequest.getAddress());
        return restaurantRepository.save(persistedRestaurant);


    }

}
