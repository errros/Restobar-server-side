package com.errros.Restobar.entities;


import com.errros.Restobar.models.SupplierRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Data
@Entity
public class Supplier {


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull
    @NotBlank
    private String name;


    private String address;

    private String city;

    @Size(min = 9,max = 10)
    private String phoneNumber1;

    @Size(min = 9,max = 10)
    private String phoneNumber2;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<Product> products = new ArrayList<>();


    public Supplier(SupplierRequest supplierRequest) {
        this.name = supplierRequest.getName();
        this.address = supplierRequest.getAddress();
        this.city = supplierRequest.getCity();
        this.phoneNumber1 = supplierRequest.getPhoneNumber1();
        this.phoneNumber2 = supplierRequest.getPhoneNumber2();





    }

    public void addProduct(Product product){
        this.products.add(product);
        product.setSupplier(this);
    }

    public void removeProduct(Product product){
        this.products.remove(product);
        product.setSupplier(null);
    }

}
