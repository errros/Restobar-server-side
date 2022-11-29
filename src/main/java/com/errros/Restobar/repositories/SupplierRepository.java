package com.errros.Restobar.repositories;

import com.errros.Restobar.entities.Client;
import com.errros.Restobar.entities.Restaurant;
import com.errros.Restobar.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {

    public List<Supplier> findByRestaurant(Restaurant restaurant);

}
