package com.errros.Restobar.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class CategoryRequest {


    @NotNull
    private String name;


    private String description;

    private Long tvaOnTableId = 0l;
    private Long tvaTakenAwayId = 0l;

}
