package com.errros.Restobar.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "product")
    private Image image;

    @JsonIgnore
    @ManyToOne
    private Category category;

    @JsonIgnore
    @ManyToOne
    private SubCategory subCategory;




}
