package com.errros.Restobar.entities;


import com.errros.Restobar.models.UserRequest;
import com.errros.Restobar.models.UserRole;
import lombok.*;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorOptions(force = true)
public class User {


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private String username;
    @Column
    @NotNull
    @NotBlank( message = "wrong first name")
    private String firstname;
    @Column
    @NotNull
    @NotBlank( message = "wrong family name")
    private String secondname;



    //password should be at least 9 characters long
    @Column
    @NotNull
    @Size(min = 8 , message = "password size should be longer than 8 characters")
    private String password;
    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;



    @Column
    @NotNull
    @Email
    private String email;


    private String address;

    private String phoneNumber;

    @NotNull
    private Boolean active = true;


    public User(UserRequest userRequest) {
        this.username = userRequest.getUsername();
        this.firstname = userRequest.getFirstname();
        this.secondname = userRequest.getSecondname();
        this.email = userRequest.getEmail();
        this.password = userRequest.getPassword();
        this.phoneNumber = userRequest.getPhoneNumber();
        this.address = userRequest.getAddress();
    }

    public User(Long id, String username, String firstname, String seoncdname, String password, UserRole role, String email, String address, String phoneNumber, Boolean active) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.secondname = seoncdname;
        this.password = password;
        this.role = role;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.active = active;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", secondname='" + secondname + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", active=" + active +
                '}';
    }
}

