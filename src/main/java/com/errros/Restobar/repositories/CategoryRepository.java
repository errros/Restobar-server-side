package com.errros.Restobar.repositories;

import com.errros.Restobar.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Transactional
    public void deleteById(Long id);

}
