package com.errros.Restobar.repositories;

import com.errros.Restobar.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
}
