package com.example.securityjwtdemo.service.impl;

import com.example.securityjwtdemo.model.Role;
import com.example.securityjwtdemo.model.RoleName;
import com.example.securityjwtdemo.repository.IRoleRepository;
import com.example.securityjwtdemo.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoleServiceImpl implements IRoleService {
@Autowired
    IRoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
