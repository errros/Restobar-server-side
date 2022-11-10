package com.errros.Restobar.repositories;

import com.errros.Restobar.entities.Sys_Admin;
import com.errros.Restobar.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Sys_AdminRepository extends JpaRepository<Sys_Admin,Long> {
    @Query
    Optional<User> findByUsername(String username);

    @Query
    Optional<User> findByEmail(String email);

    @Query
    void deleteById(Long id);

}
