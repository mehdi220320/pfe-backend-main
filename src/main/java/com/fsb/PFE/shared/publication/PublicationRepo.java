package com.fsb.PFE.shared.publication;

import com.fsb.PFE.shared.contenue_publication.ContenuePub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepo extends JpaRepository<Publication, Integer> {
    List<Publication> findAllByOwner (String idOwner);
    Publication findByContenuId(int idContenu);
}
