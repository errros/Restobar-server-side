package com.errros.Restobar.controllers;

import com.errros.Restobar.entities.Supplier;
import com.errros.Restobar.models.SupplierRequest;
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
public class SupplierController {


    @Autowired
    private RestaurantService restaurantService;


    @Operation(summary = "get all supplierss",
            description = "operation can be done by sys_admin or a restaurant owner ",security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping(path = "supplier")
    public ResponseEntity<List<Supplier>> getAllSuppliers(@PathVariable("restaurant_id") Long idRestaurant){
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getAllSuppliers(idRestaurant));
    }


    @Operation(summary = "create a supplier ",
            description = "operation can be done by sys_admin or a restaurant owner ",security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping(path = "supplier")
    public ResponseEntity<Supplier> createSupplier(@PathVariable("restaurant_id") Long idRestaurant,
                                                   @RequestBody @Valid SupplierRequest supplierRequest
    ){
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.createSupplier(idRestaurant,supplierRequest));
    }

    @Operation(summary = "update a supplier",
            description = "operation can be done by sys_admin or a restaurant owner ",security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping(path = "supplier/{supplier_id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable("restaurant_id") Long idRestaurant ,
                                                   @PathVariable("supplier_id") Long idSupplier,
                                                   @RequestBody @Valid SupplierRequest supplierRequest
    ){
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.updateSupplier(idRestaurant,idSupplier,supplierRequest));
    }
    @Operation(summary = "delete a supplier",
            description = "operation can be done by sys_admin or a restaurant owner ",security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping(path = "supplier/{supplier_id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable("restaurant_id") Long idRestaurant ,
                                                 @PathVariable("supplier_id") Long idSupplier){
        restaurantService.deleteSupplier(idRestaurant,idSupplier);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully!");
    }


    @Operation(summary = "add a supplier for a product ",
            description = "operation can be done by sys_admin or a restaurant owner ",security = {@SecurityRequirement(name = "bearer-key")})
    @PatchMapping(path = "product/{product_id}/supplier/{supplier_id}")
    public ResponseEntity<Supplier> addSupplierOfProduct(
              @PathVariable("restaurant_id") Long idRestaurant ,
                 @PathVariable("product_id") Long idProduct,
                                                         @PathVariable("supplier_id") Long idSupplier){

        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.addSupplierOfProduct(idRestaurant,idProduct,idSupplier));

    }



}
