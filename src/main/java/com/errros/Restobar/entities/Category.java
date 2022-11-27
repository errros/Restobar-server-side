package com.errros.Restobar.entities;


import com.errros.Restobar.models.CategoryRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Data
@Entity
public class Category {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull
    @Size(min = 5)
    private String name;


    private String description;

    @OneToOne(fetch = FetchType.EAGER,mappedBy = "category" , cascade = CascadeType.ALL , orphanRemoval = true)
    private Image image;



    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;



    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category")
    private List<SubCategory> subCategories = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category")
    private List<Product> products  = new ArrayList<>();


    @Size(max = 2)
    @ManyToMany(mappedBy = "categories")
    private List<Tva> tvas = new ArrayList<>();



    public Category(CategoryRequest categoryRequest) {
        this.name = categoryRequest.getName();
        this.description = categoryRequest.getDescription();
    }

    public void addTva(Tva tva){
        tvas.add(tva);
        tva.getCategories().add(this);
    }
    public void clearTvas(){
        this.getTvas().forEach(tva -> tva.getCategories().remove(this));
        this.getTvas().clear();
    }

    public void addSubCategory(SubCategory subCategory){
        this.getSubCategories().add(subCategory);
        subCategory.setCategory(this);
    }
    public void removeSubCategory(SubCategory subCategory){
        this.getSubCategories().remove(subCategory);
        subCategory.setCategory(null);
    }


    public void addProduct(Product product) {
        this.products.add(product);
        product.setCategory(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        product.setCategory(null);
    }



    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", subCategories=" + subCategories +
                ", products=" + products +
                '}';
    }

    public void addProduct() {
    }
}
