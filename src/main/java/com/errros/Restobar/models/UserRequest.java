package com.errros.Restobar.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotNull
    private String username;
    @Column
    @NotNull
    @NotBlank( message = "wrong first name")
    private String firstname;

    @NotNull
    @NotBlank( message = "wrong family name")
    private String secondname;



    //password should be at least 9 characters long
    @NotNull
    @Size(min = 8 , message = "password size should be longer than 8 characters")
    private String password;




    @NotNull
    @Email
    private String email;


    private String address;

    private String phoneNumber;






}
