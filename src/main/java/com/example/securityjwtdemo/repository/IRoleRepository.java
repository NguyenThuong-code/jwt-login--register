package com.example.securityjwtdemo.repository;

import com.example.securityjwtdemo.model.Role;
import com.example.securityjwtdemo.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
