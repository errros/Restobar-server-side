package com.errros.Restobar.controllers;


import com.errros.Restobar.entities.Cashier;
import com.errros.Restobar.entities.Owner;
import com.errros.Restobar.entities.Restaurant;
import com.errros.Restobar.models.RestaurantRequest;
import com.errros.Restobar.models.UserRequest;
import com.errros.Restobar.models.UserRole;
import com.errros.Restobar.services.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("api/restaurant")
@PreAuthorize(value = "hasAuthority('SYS_ADMIN')")
public class RestaurantsManagmentController {

    @Autowired
    private RestaurantService restaurantService;



    @Operation(summary = "get a list of all restaurants ", security = {@SecurityRequirement(name = "bearer-key")})

    @GetMapping
    public ResponseEntity<String> getAllRestaurants(){

        var responseBody = restaurantService.getAllRestaurants();
       return ResponseEntity.status(HttpStatus.OK).body(responseBody.toString());

    }

    @Operation(summary = "get a specific restaurant", security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("{id}")
    public ResponseEntity<String> getRestaurant(@PathVariable(value = "id",required = true) Long id){

        var responseBody = restaurantService.getRestaurant(id);
       if(responseBody.isPresent()) {
           return ResponseEntity.status(HttpStatus.OK).body(responseBody.get().toString());
       }else {
           return ResponseEntity.status(HttpStatus.NO_CONTENT).body("There's no restaurant with such an id!");
       }
    }
    @Operation(summary = "create a restaurant", security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping
    public ResponseEntity<String> createRestaurant(@RequestBody @Valid RestaurantRequest restaurant){

        System.out.println(restaurant);
        var responseBody = restaurantService.createRestaurant(restaurant);
       return ResponseEntity.status(HttpStatus.OK).body(responseBody.toString());

    }

    @Operation(summary = "delete a restaurant", security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable(value = "id",required = true) Long id){

         restaurantService.deleteRestaurant(id);
       return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully!");
    }

    @Operation(summary = "update a restaurant infos", security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping("{id}")
    public ResponseEntity<String> updateRestaurant(@PathVariable(value = "id",required = true) Long id ,
                                             @RequestBody @Valid RestaurantRequest restaurantRequest){
        var responseBody = restaurantService.updateRestaurant(id,restaurantRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody.toString());
    }



    @Operation(summary = "register an owner of a restaurant", security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping("/{idres}/{iduser}/owner")
    public ResponseEntity<String> addOwner(@PathVariable(value = "idres",required = true) Long idres ,
                                           @PathVariable(value = "iduser",required = false) Long iduser,
                                           @RequestBody @Valid UserRequest ownerRequest){


         var restaurant = restaurantService.addOwner(idres,iduser,ownerRequest);
return ResponseEntity.status(HttpStatus.OK).body(restaurant.toString());

    }

    @Operation(summary = "register a cashier in a restaurant", security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping("/{idres}/{iduser}/cashier")
    public ResponseEntity<String> addCashier(@PathVariable(value = "idres",required = true) Long id ,
                                             @PathVariable(value = "iduser",required = false) Long iduser,
                                     @RequestBody @Valid UserRequest cashierRequest){

        var restaurant = restaurantService.addCashier(id,iduser,cashierRequest);
        return ResponseEntity.status(HttpStatus.OK).body(restaurant.toString());

    }
/*

    @PostMapping("{id}/{idOwner}")
    public ResponseEntity<String> deleteOwner(@PathVariable(value = "id",required = true) Long id ,
                                     @PathVariable(value = "idOwner",required = true) Long idOwner,
                                     @RequestBody @Valid Owner owner){


        var responseBody = restaurantService.deleteOwner(id,idOwner,owner);


    }
    @PostMapping("{id}/{idCashier}")
    public ResponseEntity<String> deleteCashier(@PathVariable(value = "id",required = true) Long id ,
                                          @PathVariable(value = "idCashier",required = true) Long idCashier ,
                                     @RequestBody @Valid Cashier cashier){

        var responseBody = restaurantService.deleteCashier(id,idCashier,cashier);



    }


*/




}
