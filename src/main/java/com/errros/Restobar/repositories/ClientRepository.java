package com.errros.Restobar.repositories;

import com.errros.Restobar.entities.Client;
import com.errros.Restobar.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    public List<Client> findByRestaurant(Restaurant restaurant);
}
