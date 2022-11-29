package com.errros.Restobar.controllers;


import com.errros.Restobar.entities.Tva;
import com.errros.Restobar.services.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/restaurant/{restaurant_id}")
@PreAuthorize("hasAuthority('SYS_ADMIN') or (hasAuthority('OWNER') and #idRestaurant == authentication.principal.user.restaurant.id)")

public class TvaController {


    @Autowired
    private RestaurantService restaurantService;


    @Operation(summary = "get all Tva of a restaurant",
            description = "operation can be done by sys_admin or a restaurant owner ",security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping(path = "tva")
    public ResponseEntity<List<Tva>> getAllTvas(@PathVariable("restaurant_id") Long idRestaurant){
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getAllTva(idRestaurant));
    }

    @Operation(summary = "create a Tva , tva value in percent",
            description = "operation can be done by sys_admin or a restaurant owner ",security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping(path = "tva")
    public ResponseEntity<Tva> createTva(@PathVariable("restaurant_id") Long idRestaurant ,
                                         @RequestParam String  tvaName,
                                         @RequestParam Integer tvaValue){


        Tva tva = restaurantService.createTva(idRestaurant,tvaName,tvaValue);
        return ResponseEntity.status(HttpStatus.OK).body(tva);
    }

    @Operation(summary = "remove a Tva ",
            description = "operation can be done by sys_admin or a restaurant owner ",security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping(path = "tva/{tva_id}")
    public ResponseEntity<String> removeTva(@PathVariable("restaurant_id") Long idRestaurant ,
                                            @PathVariable("tva_id") Long idTva
    ){


        restaurantService.deleteTva(idRestaurant,idTva);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully!");
    }



}
