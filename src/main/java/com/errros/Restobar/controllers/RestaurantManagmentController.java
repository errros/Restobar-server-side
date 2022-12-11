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
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/restaurant")
@CrossOrigin(origins = "*")
public class RestaurantManagmentController {

    @Autowired
    private RestaurantService restaurantService;



    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    @Operation(summary = "get a list of all restaurants ",
            description = "only sys_admin can do this operation",
            security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants(){
        var responseBody = restaurantService.getAllRestaurants();
       return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }


    @PreAuthorize(value = "hasAuthority('SYS_ADMIN') or #idRestaurant == authentication.principal.user.restaurant.id")
    @Operation(summary = "get a specific restaurant",
             description = "a sysadmin , an owner or a cashier of this restaurant can retrieve it",
            security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("{id_restaurant}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable(value = "id_restaurant",required = true) Long idRestaurant){

        var responseBody = restaurantService.getRestaurant(idRestaurant);
   return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }


    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    @Operation(summary = "create a restaurant",
            description = "operation can be done by sys_admin only",security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Restaurant> createRestaurant(@RequestPart @Valid RestaurantRequest restaurant , @RequestPart(required = false) MultipartFile img){


        if(Objects.nonNull(img) && !(img.getContentType().contains("png") ||img.getContentType().contains("jpg") || img.getContentType().contains("jpeg")) ){
            throw new OpenApiResourceNotFoundException("The uploaded file should be an image (png , jpg ,jpeg)!");
        }


        var responseBody = restaurantService.createRestaurant(restaurant,img);
       return ResponseEntity.status(HttpStatus.OK).body(responseBody);

    }


    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    @Operation(summary = "delete a restaurant",
            description = "opearation can be done only by sys_admin" ,
            security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable(value = "id",required = true) Long id){
         restaurantService.deleteRestaurant(id);
       return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully!");
    }

    @PreAuthorize(value = "hasAuthority('SYS_ADMIN') or (hasAuthority('OWNER') and #idRestaurant == authentication.principal.user.restaurant.id)")
    @Operation(summary = "update a restaurant infos",
            description = "operation can be done by a sysadmin or an owner of the restauarant",
            security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping(value = "{restaurant_id}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable(value = "restaurant_id",required = true) Long idRestaurant ,
                                             @RequestPart @Valid RestaurantRequest restaurant,@RequestPart(required = false) MultipartFile img){


        if(Objects.nonNull(img) && !(img.getContentType().contains("png") ||img.getContentType().contains("jpg") || img.getContentType().contains("jpeg")) ){
            throw new OpenApiResourceNotFoundException("The uploaded file should be an image (png , jpg ,jpeg)!");
        }


        var responseBody = restaurantService.updateRestaurant(idRestaurant,restaurant,img);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }


    @PreAuthorize("hasAuthority('OWNER') AND #idres == authentication.principal.restaurant.id OR hasAuthority('SYS_ADMIN')")
            @Operation(summary = "register an owner of a restaurant",
                    description = "either a sysadmin , or an owner of the restaurant can do",
                    security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping("/{id_restaurant}/owner")
    public ResponseEntity<Restaurant> addOwner(@PathVariable(value = "id_restaurant",required = true) Long idres ,
                                           @RequestBody @Valid UserRequest ownerRequest){

         var restaurant = restaurantService.addOwner(idres,ownerRequest);
return ResponseEntity.status(HttpStatus.OK).body(restaurant);

    }

    @PreAuthorize("hasAuthority('OWNER') AND #id == authentication.principal.user.restaurant.id OR hasAuthority('SYS_ADMIN')")
    @Operation(summary = "register a cashier in a restaurant",
            description = "operation can be done by sys_admin or an owner of that restaurant",security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping("/{id_restaurant}/cashier")
    public ResponseEntity<Restaurant> addCashier(@PathVariable(value = "id_restaurant",required = true) Long id ,
                                     @RequestBody @Valid UserRequest cashierRequest){

        var restaurant = restaurantService.addCashier(id,cashierRequest);
        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }


    @PreAuthorize("hasAuthority('OWNER') AND #idRestaurant == authentication.principal.user.restaurant.id OR hasAuthority('SYS_ADMIN')")
    @Operation(summary = "delete an owner ", description = "either a sysadmin , or an owner of the restaurant can do the operation"
            ,security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping("{id_restaurant}/owner/{id_owner}")
    public ResponseEntity<String> deleteOwner(@PathVariable(value = "id_restaurant",required = true) Long idRestaurant,
                                     @PathVariable(value = "id_owner") Long idOwner){
         restaurantService.deleteOwner(idRestaurant,idOwner);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Deleted owner with id %d from restaurant with id %d successfuly ",idOwner,idRestaurant));

    }

    @PreAuthorize("hasAuthority('OWNER') and #idRestaurant == authentication.principal.user.restaurant.id OR hasAuthority('SYS_ADMIN')")
    @Operation(summary = "delete a cashier ", description = "either a sysadmin , or an owner of the restaurant can do"
            ,security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping("{id_restaurant}/cashier/{id_cashier}")
    public ResponseEntity<String> deleteCashier(@PathVariable(value = "id_restaurant",required = true) Long idRestaurant ,
                                     @PathVariable(value = "id_cashier") Long idCashier){
       restaurantService.deleteCashier(idRestaurant,idCashier);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Deleted cashier with id %d from restaurant with id %d successfuly ",idCashier,idRestaurant));

    }






}
