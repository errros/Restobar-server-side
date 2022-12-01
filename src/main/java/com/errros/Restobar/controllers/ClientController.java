package com.errros.Restobar.controllers;

import com.errros.Restobar.entities.Client;
import com.errros.Restobar.models.ClientRequest;
import com.errros.Restobar.services.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/restaurant/{restaurant_id}")
@PreAuthorize("hasAuthority('SYS_ADMIN') or (hasAuthority('OWNER') and #idRestaurant == authentication.principal.user.restaurant.id)")
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    private RestaurantService restaurantService;


    @Operation(summary = "get all clients",
            description = "operation can be done by sys_admin or a restaurant owner ",security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping(path = "client")
    public ResponseEntity<List<Client>> getAllClients(@PathVariable("restaurant_id") Long idRestaurant){
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getAllClients(idRestaurant));
    }



    @Operation(summary = "add a client",
            description = "operation can be done by sys_admin or a restaurant owner ",security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping(path = "client")
    public ResponseEntity<Client> createClient(@PathVariable("restaurant_id") Long idRestaurant ,
                                               @RequestBody @Valid ClientRequest clientRequest
    ){
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.createClient(idRestaurant,clientRequest));
    }


    @Operation(summary = "update a client",
            description = "operation can be done by sys_admin or a restaurant owner ",security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping(path = "client/{client_id}")
    public ResponseEntity<Client> updateClient(@PathVariable("restaurant_id") Long idRestaurant ,
                                               @PathVariable("client_id") Long idClient,
                                               @RequestBody @Valid ClientRequest clientRequest
    ){
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.updateClient(idRestaurant,idClient,clientRequest));
    }



    @Operation(summary = "delete a client",
            description = "operation can be done by sys_admin or a restaurant owner ",security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping(path = "client/{client_id}")
    public ResponseEntity<String> deleteClient(@PathVariable("restaurant_id") Long idRestaurant ,
                                               @PathVariable("client_id") Long idClient){
        restaurantService.deleteClient(idRestaurant,idClient);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully!");
    }




}
