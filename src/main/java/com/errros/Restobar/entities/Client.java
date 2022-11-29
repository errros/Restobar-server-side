package com.errros.Restobar.entities;


import com.errros.Restobar.models.ClientRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Data
@Entity
public class Client {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank
    private String firstname;

    private String secondname;

    private String address;

    private String city;

    @Size(min = 9,max = 10)
    private String phoneNumber;

    @PositiveOrZero
    private Integer permanentDiscount = 0;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;





    public Client(ClientRequest clientRequest) {
        this.firstname = clientRequest.getFirstname();
        this.secondname = clientRequest.getSecondname();
        this.address = clientRequest.getAddress();
        this.city = clientRequest.getCity();
        this.phoneNumber = clientRequest.getPhoneNumber();
        this.permanentDiscount = clientRequest.getPermanentDiscount();
    }

}
