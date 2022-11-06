package com.errros.Restobar.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public
interface UserRepository extends JpaRepository<User, Long> {

    @Query
    Optional<User> findByUsername(String username);

    @Query
    Optional<User> findByEmail(String email);

    @Query
    void deleteById(Long id);


}
