package com.errros.Restobar.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class ClientRequest {


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


}
