package com.errros.Restobar.entities;


import com.errros.Restobar.models.ProductRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

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


    @PositiveOrZero
    private Integer qtyStock ;

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

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;


    @ManyToMany(mappedBy = "product")
    private List<Accompaniment> accompaniments = new ArrayList<>();



    public void addAccompaniment(Accompaniment accompaniment) {
        this.accompaniments.add(accompaniment);
        accompaniment.setProduct(this);
    }

    public void removeAccompaniment(Accompaniment accompaniment) {
        this.accompaniments.remove(accompaniment);
        accompaniment.setProduct(null);
    }




    public Product(ProductRequest productRequest) {
        this.name = productRequest.getName();
        this.manageSizes = productRequest.getManageSizes();
        if(!manageSizes) {
            this.priceOnTable = productRequest.getPriceOnTable();
            this.priceTakenAway = productRequest.getPriceTakenAway();

        }else {
            this.priceOnTableS = productRequest.getPriceOnTableS();
            this.priceTakenAwayS = productRequest.getPriceTakenAwayS();

            this.priceOnTableM = productRequest.getPriceOnTableM();
            this.priceTakenAwayM = productRequest.getPriceTakenAwayM();


            this.priceOnTableL = productRequest.getPriceOnTableL();
            this.priceTakenAwayL = productRequest.getPriceTakenAwayL();

        }

        this.allowDiscount = productRequest.getAllowDiscount();
        this.qtyStock = productRequest.getQtyStock();
        this.buyingPrice = productRequest.getBuyingPrice();
    }
}
