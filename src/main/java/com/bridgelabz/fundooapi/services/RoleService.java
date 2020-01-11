package com.bridgelabz.restapi.services;



import java.util.Optional;

import com.bridgelabz.restapi.model.Role;
import com.bridgelabz.restapi.model.RoleName;


public interface RoleService {
    Optional<Role> findByName(RoleName roleName);
}
