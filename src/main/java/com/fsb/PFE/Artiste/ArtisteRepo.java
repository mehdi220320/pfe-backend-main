package com.fsb.PFE.Artiste;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtisteRepo extends JpaRepository<Artiste, Integer> {
    Artiste findArtisteByUserName(String userName);

}
