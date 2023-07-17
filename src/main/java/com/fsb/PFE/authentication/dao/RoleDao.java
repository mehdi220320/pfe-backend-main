package com.fsb.PFE.authentication.dao;


import com.fsb.PFE.authentication.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {
    Role findRoleByRoleName(String rolename);
}
