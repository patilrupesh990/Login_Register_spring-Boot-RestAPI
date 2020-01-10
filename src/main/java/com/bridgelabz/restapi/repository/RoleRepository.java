package com.bridgelabz.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.restapi.model.Role;
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer>{

	Role findByRole(String role);
}
