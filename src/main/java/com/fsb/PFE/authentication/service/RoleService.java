package com.fsb.PFE.authentication.service;

import com.fsb.PFE.authentication.dao.RoleDao;
import com.fsb.PFE.authentication.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }
}
