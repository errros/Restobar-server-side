package com.errros.Restobar.controllers;


import com.errros.Restobar.entities.Category;
import com.errros.Restobar.models.CategoryRequest;
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

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "api/restaurant/{restaurant_id}")
@PreAuthorize("hasAuthority('SYS_ADMIN') or (hasAuthority('OWNER') and #idRestaurant == authentication.principal.user.restaurant.id)")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private RestaurantService restaurantService;


    @Operation(summary = "retrieve all categories in a restaurant",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping(path = "category")
    public ResponseEntity<List<Category>> getAllCategories(@PathVariable("restaurant_id") Long idRestaurant){
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getAllCategories(idRestaurant));
    }



    @Operation(summary = "get a single category",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping(path = "category/{category_id}")
    public ResponseEntity<Category> getCategory(@PathVariable("restaurant_id") Long idRestaurant ,
                                                @PathVariable("category_id") Long idCategory){

        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getCategory(idRestaurant,idCategory));
    }


    @Operation(summary = "create a category of products",
            description = "operation can be done by sys_admin or a restaurant owner , img types accepted : png,jpg,jpeg",security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping(path = "category", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Category> createCategory(@PathVariable("restaurant_id") Long idRestaurant ,
                                                   @Valid @RequestPart CategoryRequest categoryRequest,
                                                   @RequestPart(required = false) MultipartFile img){

        if(Objects.nonNull(img) && !(img.getContentType().contains("png") ||img.getContentType().contains("jpg") || img.getContentType().contains("jpeg")) ){
            throw new OpenApiResourceNotFoundException("The uploaded file should be an image (png , jpg ,jpeg)!");
        }
        Category category = restaurantService.createCategory(idRestaurant,categoryRequest,img);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }




    @Operation(summary = "update a category of products",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping(path = "category/{category_id}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Category> updateCategory(@PathVariable("restaurant_id") Long idRestaurant ,
                                                   @PathVariable("category_id") Long idCategory,
                                                   @Valid @RequestPart CategoryRequest categoryRequest,
                                                   @RequestPart(required = false) MultipartFile img){



        if(Objects.nonNull(img) && !(img.getContentType().contains("png") ||img.getContentType().contains("jpg") || img.getContentType().contains("jpeg")) ){
            throw new OpenApiResourceNotFoundException("The uploaded file should be an image (png , jpg ,jpeg)!");
        }

        Category category = restaurantService.updateCategory(idRestaurant,idCategory,categoryRequest,img);


        return ResponseEntity.status(HttpStatus.OK).body(category);
    }
    @Operation(summary = "delete a category of products (will delete subcategories , products , images too ...)",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping(path = "category/{category_id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("restaurant_id") Long idRestaurant ,
                                                 @PathVariable("category_id") Long idCategory){
        restaurantService.deleteCategory(idRestaurant,idCategory);
        return ResponseEntity.status(HttpStatus.OK).body("Category with it all subcategories,products , images ... had been deleted successfully!");
    }






}
