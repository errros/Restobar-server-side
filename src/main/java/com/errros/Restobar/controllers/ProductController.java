package com.errros.Restobar.controllers;


import com.errros.Restobar.entities.Accompaniment;
import com.errros.Restobar.entities.Product;
import com.errros.Restobar.models.AccompanimentType;
import com.errros.Restobar.models.ProductRequest;
import com.errros.Restobar.services.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping(value = "api/restaurant/{restaurant_id}")
@PreAuthorize("hasAuthority('SYS_ADMIN') or (hasAuthority('OWNER') and #idRestaurant == authentication.principal.user.restaurant.id)")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private RestaurantService restaurantService;


    @Operation(summary = "get a single product",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping(path = "product/{product_id}")
    public ResponseEntity<Product> getProduct(@PathVariable("restaurant_id") Long idRestaurant ,
                                              @PathVariable("product_id") Long idProduct){

        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getProduct(idRestaurant,idProduct));

    }




    @Operation(summary = "add a product to a category",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping(path = "category/{category_id}/product" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Product> createProductInsideCategory(@PathVariable("restaurant_id") Long idRestaurant ,
                                                               @PathVariable("category_id") Long idCategory,
                                                               @RequestPart ProductRequest productRequest ,
                                                               @RequestPart(required = false) MultipartFile img
    ){

        if(Objects.nonNull(img) && !(img.getContentType().contains("png") ||img.getContentType().contains("jpg") || img.getContentType().contains("jpeg")) ){
            throw new OpenApiResourceNotFoundException("The uploaded file should be an image (png , jpg ,jpeg)!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.createProductInsideCategory(idRestaurant,idCategory,productRequest,img));

    }


    @Operation(summary = "add a product to a subcategory",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping(path = "category/{category_id}/subcategory/{subcategory_id}/product" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Product> createProductInsideSubCategory(@PathVariable("restaurant_id") Long idRestaurant ,
                                                                  @PathVariable("category_id") Long idCategory,
                                                                  @PathVariable("subcategory_id") Long idSubCategory,
                                                                  @RequestPart ProductRequest productRequest ,
                                                                  @RequestPart(required = false) MultipartFile img
    ){

        if(Objects.nonNull(img) && !(img.getContentType().contains("png") ||img.getContentType().contains("jpg") || img.getContentType().contains("jpeg")) ){
            throw new OpenApiResourceNotFoundException("The uploaded file should be an image (png , jpg ,jpeg)!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.createProductInsideSubCategory(idRestaurant,idCategory,idSubCategory,productRequest,img));


    }


    @Operation(summary = "delete a product (it will remove it from category or subcategory too)",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping(path = "product/{product_id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("restaurant_id") Long idRestaurant ,
                                                @PathVariable("product_id") Long idProduct
    ){
        restaurantService.deleteProduct(idRestaurant,idProduct);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted product successfully!");

    }

    @Operation(summary = "update a product ",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping(path = "product/{product_id}" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Product> updateProduct(@PathVariable("restaurant_id") Long idRestaurant ,
                                                 @PathVariable("product_id") Long idProduct,
                                                 @RequestPart ProductRequest productRequest ,
                                                 @RequestPart(required = false) MultipartFile img

    ){

        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.updateProduct(idRestaurant,idProduct,productRequest,img));

    }


    @Operation(summary = "accompany a product with a category ",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping(path = "product/{product_id}/accompaniment")
    public ResponseEntity<Accompaniment> createAccompaniment(@PathVariable("restaurant_id") Long idRestaurant ,
                                                             @PathVariable("product_id") Long idProduct,
                                                             @RequestParam(name = "category") Long idCategory,
                                                             @RequestParam(name = "type") AccompanimentType type,
                                                             @RequestParam(required = false , name = "nbr") Integer nbr
    ){

        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.createAccompaniment(idRestaurant,idProduct,idCategory,type,nbr));
    }



    @Operation(summary = "delete an accompaniment between a product and a category",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping(path = "product/{product_id}/accompaniment/{accompaniment_id}")

    public ResponseEntity<String> removeAccompaniment(@PathVariable("restaurant_id") Long idRestaurant ,
                                                      @PathVariable("product_id") Long idProduct,
                                                      @PathVariable("accompaniment_id") Long idAccompaniment
                                                      ){

        restaurantService.deleteAccompaniment(idRestaurant,idProduct,idAccompaniment);
return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully!");
    }




}
