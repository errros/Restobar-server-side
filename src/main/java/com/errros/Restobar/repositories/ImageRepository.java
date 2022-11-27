package com.errros.Restobar.repositories;

import com.errros.Restobar.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface ImageRepository extends JpaRepository<Image,Long> {

   public void deleteByCategory(Category category);

   public void deleteByProduct(Product product);

   public void deleteBySubCategory(SubCategory subCategory);

   public void deleteByRestaurant(Restaurant restaurant);


}
