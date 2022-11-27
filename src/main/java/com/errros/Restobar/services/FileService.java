package com.errros.Restobar.services;


import com.errros.Restobar.entities.Category;
import com.errros.Restobar.entities.Product;
import com.errros.Restobar.entities.Restaurant;
import com.errros.Restobar.entities.SubCategory;
import com.errros.Restobar.models.FileType;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import static com.errros.Restobar.models.FileType.*;
@Service
public class FileService {
    public static final Path root = Paths.get("uploads");
    public static final Path restaurantsPath = Paths.get(root.toString(),"restaurants");
    public static final Path categoriesPath = Paths.get(root.toString(),"categories");
    public static final Path subCategoriesPath = Paths.get(root.toString(),"subcategories");
    public static final Path productsPath = Paths.get(root.toString(),"products");

    public void init() {
        try {
            if(Files.notExists(root)) {

                Files.createDirectory(root);
                Files.createDirectories(restaurantsPath);
                Files.createDirectories(categoriesPath);
                Files.createDirectories(subCategoriesPath);
                Files.createDirectories(productsPath);


            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public Path save(MultipartFile file, String filename, FileType type) {
        try {
            init();
            //get file extension and add it to the filename
            var filetype =  file.getContentType().split("/")[1];
            filename = filename.trim()+"."+filetype.trim();

            //delete file if it exists already
            Files.deleteIfExists(type.getUploadPath().resolve(filename));


            Files.copy(file.getInputStream(), type.getUploadPath().resolve(filename));

             //return the absolute path of the file
            return type.getUploadPath().resolve(filename).toAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

    }


    public String generateFileName(MultipartFile file,FileType type , Object obj){

        switch (type){
            case IMG_CATEGORY -> {
                Category category = (Category) obj;
                return """
                       r%d-c%d       
                       """.formatted(category.getRestaurant().getId(),category.getId());
            }
            case IMG_RESTAURANT -> {
                Restaurant restaurant = (Restaurant) obj;
                return """
                       r%d       
                       """.formatted(restaurant.getId());
            }
            case IMG_PRODUCT -> {
                Product product = (Product) obj;
                Long restaurantId = Objects.nonNull(product.getCategory()) ?
                        product.getCategory().getRestaurant().getId() :
                        product.getSubCategory().getCategory().getRestaurant().getId();
                return """
                       r%d-p%d       
                       """.formatted(restaurantId ,product.getId());

            }
            case IMG_SUBCATEGORY -> {
                SubCategory subCategory = (SubCategory) obj;
                return """
                       r%d-sc%d       
                       """.formatted(subCategory.getCategory().getRestaurant().getId() ,subCategory.getId());
            }

        }
      return null;
    }

    public Resource load(String filename) {
        try {
            init();
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }



}

