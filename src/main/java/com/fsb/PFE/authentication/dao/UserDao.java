package com.fsb.PFE.authentication.dao;

import com.fsb.PFE.authentication.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, String> {
    User findByProfileId(String profileId);
    User findByName(String name);

}
