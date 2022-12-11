package com.errros.Restobar.entities;


import com.errros.Restobar.models.AccompanimentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Data
@Entity
public class Accompaniment {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private AccompanimentType type;


    private Integer nbr;



    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;



    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    public Accompaniment(AccompanimentType type, Integer nbr, Category category) {
        this.type = type;
        this.nbr = nbr;
        this.category = category;
    }

    public Accompaniment(AccompanimentType type,Category category) {
        this.type = type;
        this.category = category;
    }
}
