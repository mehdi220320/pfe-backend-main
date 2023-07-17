package com.fsb.PFE.shared.contenue_publication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContenuePubRepo extends JpaRepository<ContenuePub, Integer> {


}
