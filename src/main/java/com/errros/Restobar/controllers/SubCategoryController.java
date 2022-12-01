package com.errros.Restobar.controllers;

import com.errros.Restobar.entities.SubCategory;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@RestController
@RequestMapping(value = "api/restaurant/{restaurant_id}")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('SYS_ADMIN') or (hasAuthority('OWNER') and #idRestaurant == authentication.principal.user.restaurant.id)")
public class SubCategoryController {

    @Autowired
    private RestaurantService restaurantService;



    @Operation(summary = "get a subcategory of products",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping(path = "category/{category_id}/subcategory/{subcategory_id}")
    public ResponseEntity<SubCategory> getSubCategory(@PathVariable("restaurant_id") Long idRestaurant ,
                                                      @PathVariable("category_id") Long idCategory,
                                                      @PathVariable("subcategory_id") Long idSubCategory){
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getSubCategory(idRestaurant,idCategory,idSubCategory));
    }


    @Operation(summary = "create a subcategory of products",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping(path = "category/{category_id}/subcategory",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SubCategory> createSubCategory(@PathVariable("restaurant_id") Long idRestaurant ,
                                                         @PathVariable("category_id") Long idCategory,
                                                         @Valid @RequestParam @NotBlank String subCategoryName,
                                                         @RequestPart(required = false) MultipartFile img){

        if(Objects.nonNull(img) && !(img.getContentType().contains("png") ||img.getContentType().contains("jpg") || img.getContentType().contains("jpeg")) ){
            throw new OpenApiResourceNotFoundException("The uploaded file should be an image (png , jpg ,jpeg)!");
        }
        SubCategory subCategory = restaurantService.createSubCategory(idRestaurant,idCategory,subCategoryName,img);
        return ResponseEntity.status(HttpStatus.OK).body(subCategory);
    }

    @Operation(summary = "update a subcategory of products",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping(path = "category/{category_id}/subcategory/{subcategory_id}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SubCategory> updateSubCategory(@PathVariable("restaurant_id") Long idRestaurant ,
                                                         @PathVariable("category_id") Long idCategory,
                                                         @PathVariable("subcategory_id") Long idSubCategory,
                                                         @Valid @RequestParam @Size(min = 3) String subCategoryName,
                                                         @RequestPart(required = false) MultipartFile img){

        if(Objects.nonNull(img) && !(img.getContentType().contains("png") ||img.getContentType().contains("jpg") || img.getContentType().contains("jpeg")) ){
            throw new OpenApiResourceNotFoundException("The uploaded file should be an image (png , jpg ,jpeg)!");
        }
        SubCategory subCategory = restaurantService.updateSubCategory(idRestaurant,idCategory,idSubCategory,subCategoryName,img);
        return ResponseEntity.status(HttpStatus.OK).body(subCategory);
    }



    @Operation(summary = "delete a subcategory of products",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping(path = "category/{category_id}/subcategory/{subcategory_id}")
    public ResponseEntity<String> deleteSubCategory(@PathVariable("restaurant_id") Long idRestaurant ,
                                                    @PathVariable("category_id") Long idCategory,
                                                    @PathVariable("subcategory_id") Long idSubCategory){


        restaurantService.deleteSubCategory(idRestaurant,idCategory,idSubCategory);
        return ResponseEntity.status(HttpStatus.OK).body("SubCategory with it all products , images ... had been deleted successfully!");
    }





}
