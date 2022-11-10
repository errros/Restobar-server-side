package com.errros.Restobar.entities;


import com.errros.Restobar.models.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Sys_Admin extends User {

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "sys_admin")
    private List<Restaurant> restaurants = new ArrayList<>();

    public Sys_Admin(Long id, String username, String firstname, String seoncdname, String password, UserRole role, String email, String address, String phoneNumber, Boolean active) {
        super(id, username, firstname, seoncdname, password, role, email, address, phoneNumber, active);
    }

    @Override
    public String toString() {
        return super.toString();

    }
}
