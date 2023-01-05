package com.example.securityjwtdemo.service;

import com.example.securityjwtdemo.model.Role;
import com.example.securityjwtdemo.model.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
