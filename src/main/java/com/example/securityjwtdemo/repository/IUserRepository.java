package com.example.securityjwtdemo.repository;

import com.example.securityjwtdemo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String name);//find user exist in db?
    Boolean existsByUsername(String username);// had username existed in db, when create db
    Boolean existsByEmail(String email);// check email existed in db?
}
