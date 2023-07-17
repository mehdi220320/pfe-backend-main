package com.fsb.PFE.UtilisateurSimple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UtilisateurSimpleRepository extends JpaRepository<UtilisateurSimple,String> {

    UtilisateurSimple findByUserName(String userName);
    boolean existsByUserName(String userName);
    UtilisateurSimple findByProfileId(String profileId);
}
