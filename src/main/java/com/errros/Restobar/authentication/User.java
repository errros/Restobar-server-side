package com.errros.Restobar.authentication;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotNull
    private String username;

    @Column
    @NotNull
    private String firstname;

    @Column
    @NotNull
    private String secondname;



    //password should be at least 9 characters long
    @Column
    @NotNull
    @Size(min = 6 , message = "password size should be longer than 8 characters")
    private String password;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;


    @Column
    @NotNull
    @Email
    private String email;

    @Column
    private String address;

    @Column
    private String phoneNumber;


    @Column
    private Boolean active = true;

}
