package com.fsb.PFE.UtilisateurSimple;

import com.fsb.PFE.Artiste.Artiste;
import com.fsb.PFE.Artiste.FollowedArtistDTO;
import com.fsb.PFE.shared.publication.Publication;

import java.util.List;
import java.util.Set;

public interface UtilisateurImpService {
    UtilisateurSimple add(UtilisateurSimple utilisateurSimple);
    void resendCode(String utilisateurId);
    List<UtilisateurSimple> getAll();
    UtilisateurSimple update(UtilisateurSimple utilisateurSimple);
    UtilisateurSimple getById(String id);
    void delete(String id);
    Set<FollowedArtistDTO> getAllFollowing();
    Set<FollowedArtistDTO> getAllFollowers();
    void unfollow(String profileId);
    void deleteFollower(String profileId);
    List<Publication> getFollowingPosts();
}
