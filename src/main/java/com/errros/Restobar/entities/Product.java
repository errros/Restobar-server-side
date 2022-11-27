package com.errros.Restobar.entities;


import com.errros.Restobar.models.ProductRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Data
@Entity
public class Product {


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


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

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "product")
    private Image image;

    @JsonIgnore
    @ManyToOne
    private Category category;

    @JsonIgnore
    @ManyToOne
    private SubCategory subCategory;


    public Product(ProductRequest productRequest) {
        this.name = productRequest.getName();
        this.priceOnTable = productRequest.getPriceOnTable();
        this.priceTakenAway = productRequest.getPriceTakenAway();
        this.allowDiscount = productRequest.getAllowDiscount();
        this.qtyStock = productRequest.getQtyStock();
        this.buyingPrice = productRequest.getBuyingPrice();
    }
}
