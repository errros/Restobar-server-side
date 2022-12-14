package com.errros.Restobar.entities;


import com.errros.Restobar.entities.User;
import com.errros.Restobar.models.UserRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Getter
@Setter
@Entity
public class Cashier extends User {

    public Cashier(UserRequest userRequest) {
        super(userRequest);
    }

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;


    @Override
    public String toString() {
        return super.toString();
    }
}
