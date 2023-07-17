package com.fsb.PFE.shared.contenue_publication.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepo extends JpaRepository<Like, Integer> {
    List<Like> findAllByIdOwner(String idOwner);
    List<Like> findAllByIdOwnerAndId(String idOwner, int idContenuePub);
    Like findByIdOwner(String idOwner);

}
