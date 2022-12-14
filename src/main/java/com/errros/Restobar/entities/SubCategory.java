package com.errros.Restobar.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Data
@Entity
public class SubCategory {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull
    @NotBlank
    private String name;


    @OneToOne(mappedBy = "subCategory" , cascade = CascadeType.ALL)
    private Image image;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "subCategory")
    private List<Product> products  = new ArrayList<>();


    public SubCategory(String name) {
        this.name = name;
    }

    public void addProduct(Product product) {
         this.products.add(product);
         product.setSubCategory(this);

    }
    public void removeProduct(Product product) {
        this.products.remove(product);
        product.setSubCategory(null);

    }
}
