package com.errros.Restobar.authentication.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequestModel {

    @Email
    private String email;


    private String username;

    @NotNull
    private String password;

}
