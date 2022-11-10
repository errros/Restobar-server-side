package com.errros.Restobar.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class RestaurantRequest {

    @NotNull
    private String name;

    private String address;

    private String phoneNumber;

}
