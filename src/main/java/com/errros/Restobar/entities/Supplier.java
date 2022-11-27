package com.errros.Restobar.entities;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
    private String phoneNumber1;
    private String phoneNumber2;


}
