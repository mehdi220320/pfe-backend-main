package com.fsb.PFE.shared.contenue_publication.likes;

import com.fsb.PFE.authentication.entity.User;
import com.fsb.PFE.authentication.entity.UserDTO;
import com.fsb.PFE.authentication.entity.UserDTOLikes;

import java.util.List;

public interface LikeService {
    void addLike(int idContenuePub);
    void deleteLike(int idContenuePub);
    List<Like> getAllLikes(int idContenuPub);
    List<UserDTOLikes> getAllLikesNames(int idContenuPub);
}
