package com.errros.Restobar.controllers;


import com.errros.Restobar.entities.Category;
import com.errros.Restobar.entities.Product;
import com.errros.Restobar.entities.SubCategory;
import com.errros.Restobar.entities.Tva;
import com.errros.Restobar.models.CategoryRequest;
import com.errros.Restobar.models.ProductRequest;
import com.errros.Restobar.services.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "api/restaurant/{restaurant_id}")
@PreAuthorize("hasAuthority('SYS_ADMIN') or (hasAuthority('OWNER') and #idRestaurant == authentication.principal.user.restaurant.id)")
public class RestaurantController {


    private final RestaurantService restaurantService;


    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @Operation(summary = "retrieve all categories in a restaurant",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping(path = "category")
    public ResponseEntity<List<Category>> getAllCategories(@PathVariable("restaurant_id") Long idRestaurant){
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getAllCategories(idRestaurant));
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



    @Operation(summary = "get a single category",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping(path = "category/{category_id}")
    public ResponseEntity<Category> getCategory(@PathVariable("restaurant_id") Long idRestaurant ,
                                                   @PathVariable("category_id") Long idCategory){

        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getCategory(idRestaurant,idCategory));
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






    @Operation(summary = "create a subcategory of products",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping(path = "category/{category_id}/subcategory",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SubCategory> createSubCategory(@PathVariable("restaurant_id") Long idRestaurant ,
                                                    @PathVariable("category_id") Long idCategory,
                                                    @Valid @RequestParam @Size(min = 3) String subCategoryName,
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


    @Operation(summary = "get a subcategory of products",
            description = "operation can be done by sys_admin or a restaurant owner",security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping(path = "category/{category_id}/subcategory/{subcategory_id}")
    public ResponseEntity<SubCategory> getSubCategory(@PathVariable("restaurant_id") Long idRestaurant ,
                                                         @PathVariable("category_id") Long idCategory,
                                                         @PathVariable("subcategory_id") Long idSubCategory){
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getSubCategory(idRestaurant,idCategory,idSubCategory));
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
