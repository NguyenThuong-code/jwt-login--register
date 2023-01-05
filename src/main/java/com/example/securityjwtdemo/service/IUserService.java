package com.example.securityjwtdemo.service;

import com.example.securityjwtdemo.model.Users;

import java.util.Optional;

public interface IUserService {
    Optional<Users> findByUsername(String name);//find user exist in db?
    Boolean existsByUsername(String username);// had username existed in db, when create db
    Boolean existsByEmail(String email);// check email existed in db?
    Users save(Users user);

}
