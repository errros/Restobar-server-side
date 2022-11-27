package com.errros.Restobar.models;

import com.errros.Restobar.services.FileService;

import java.nio.file.Path;

public enum FileType {
    IMG_RESTAURANT(FileService.restaurantsPath),
    IMG_CATEGORY(FileService.categoriesPath),
    IMG_SUBCATEGORY(FileService.subCategoriesPath),
    IMG_PRODUCT(FileService.productsPath);

    private final Path uploadPath;


    FileType(Path uploadPath) {
        this.uploadPath = uploadPath;
    }

    public Path getUploadPath() {
        return uploadPath;
    }
}
