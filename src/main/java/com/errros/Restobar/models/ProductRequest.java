package com.errros.Restobar.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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



    @NotNull
    @PositiveOrZero
    private Integer qtyStock;

    @NotNull
    @PositiveOrZero
    private Integer buyingPrice;


    @NotNull
    private Boolean allowDiscount = true;



}
