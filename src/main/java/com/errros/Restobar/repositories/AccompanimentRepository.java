package com.errros.Restobar.repositories;

import com.errros.Restobar.entities.Accompaniment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccompanimentRepository extends JpaRepository<Accompaniment , Long> {
}
