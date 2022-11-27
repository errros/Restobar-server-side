package com.errros.Restobar.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ProductRequest {


    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @Positive
    private Integer priceOnTable;


    @NotNull
    @Positive
    private Integer priceTakenAway;




}
