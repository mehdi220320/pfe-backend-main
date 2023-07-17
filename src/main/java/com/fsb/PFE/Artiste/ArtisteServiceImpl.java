package com.fsb.PFE.Artiste;

import java.util.List;

public interface ArtisteServiceImpl {
    Artiste addArtiste(Artiste artiste);
    void resendCode(String artisteId);
    void updateArtiste(Artiste artiste);
    Artiste getArtiste(String id);
    List<Artiste> getAllArtiste();
    Following getAllFollowing();
    Following getAllFollowers();
    void deleteArtiste(Integer id);
    void follow(String id);
    void unfollow(String id);
    void deleteFollower(String id);
}
