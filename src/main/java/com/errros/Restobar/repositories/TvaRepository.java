package com.errros.Restobar.repositories;

import com.errros.Restobar.entities.Tva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvaRepository extends JpaRepository<Tva,Long> {
}
