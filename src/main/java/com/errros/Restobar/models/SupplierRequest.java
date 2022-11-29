package com.errros.Restobar.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class SupplierRequest {

    @NotNull
    @NotBlank
    private String name;


    private String address;

    private String city;

    @Size(min = 9,max = 10)
    private String phoneNumber1;

    @Size(min = 9,max = 10)
    private String phoneNumber2;


}
