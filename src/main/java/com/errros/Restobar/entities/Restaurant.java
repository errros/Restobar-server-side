package com.errros.Restobar.entities;


import javax.persistence.*;

import com.errros.Restobar.models.RestaurantRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springdoc.api.OpenApiResourceNotFoundException;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Restaurant {


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Long id;

    @Column
    @NotNull
    private String name;

    private String address;

    private String phoneNumber;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sys_admin_id")
    private Sys_Admin sys_admin;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "restaurant",cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE})
    private List<Owner> owners = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "restaurant",cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE})
    private List<Cashier> cashiers = new ArrayList<>();

    public Restaurant(RestaurantRequest restaurantRequest) {
        this.name = restaurantRequest.getName();
        this.phoneNumber = restaurantRequest.getPhoneNumber();
        this.address = restaurantRequest.getAddress();
    }

    public void addSys_admin(Sys_Admin sys_admin) {
        this.sys_admin = sys_admin;
        sys_admin.getRestaurants().add(this);
    }

    public void addOwner(Owner owner) {
     owner.setRestaurant(this);
     owners.add(owner);
    }

    public void addCashier(Cashier cashier) {
     cashier.setRestaurant(this);
     cashiers.add(cashier);
    }
    public void removeOwner(Owner owner) {

        if(owners.contains(owner)){
            if(owners.size()>1) {
                owners.remove(owner);
                owner.setRestaurant(null);
            }else {
                throw new OpenApiResourceNotFoundException("A restaurant has at least one owner!");

            }
            }

    }

    public void removeCashier(Cashier cashier) {
       if(cashiers.contains(cashier)) {
           cashier.setRestaurant(null);
           cashiers.remove(cashier);
       }
    }


    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", owners=" + owners +
                ", cashiers=" + cashiers +
                '}';
    }
}
