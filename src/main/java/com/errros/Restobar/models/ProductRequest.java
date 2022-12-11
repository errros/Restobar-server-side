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
    private Boolean manageSizes = false;


    @Positive
    private Integer priceOnTable;


    @Positive
    private Integer priceTakenAway;

    @PositiveOrZero
    private Integer priceOnTableS;


    @PositiveOrZero
    private Integer priceTakenAwayS;

    @PositiveOrZero
    private Integer priceOnTableM;


    @PositiveOrZero
    private Integer priceTakenAwayM;

    @PositiveOrZero
    private Integer priceOnTableL;


    @Positive
    private Integer priceTakenAwayL;




    @NotNull
    @PositiveOrZero
    private Integer qtyStock;

    @NotNull
    @PositiveOrZero
    private Integer buyingPrice;


    @NotNull
    private Boolean allowDiscount = true;



}
