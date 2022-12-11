package com.errros.Restobar.repositories;


import com.errros.Restobar.entities.Cashier;
import com.errros.Restobar.entities.Owner;
import com.errros.Restobar.entities.Restaurant;
import com.errros.Restobar.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

}
