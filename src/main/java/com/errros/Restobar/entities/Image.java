package com.errros.Restobar.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Data
@Entity
public class Image {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @NotNull
    @NotBlank
    private String path;



    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;


    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id",referencedColumnName = "id")
    private SubCategory subCategory;


    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;



    public Image(String title, String path,  Category category) {
        this.title = title;
        this.path = path;
        this.category = category;
    }
    public Image(String title, String path,  Restaurant restaurant) {
        this.title = title;
        this.path = path;
        this.restaurant = restaurant;
    }

    public Image(String title, String path, SubCategory subCategory) {
        this.title = title;
        this.path = path;
        this.subCategory = subCategory;
    }

    public Image(String title, String path, Product product) {
        this.title = title;
        this.path = path;
        this.product = product;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
