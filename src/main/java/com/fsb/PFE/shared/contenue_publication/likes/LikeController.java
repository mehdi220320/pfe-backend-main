package com.fsb.PFE.shared.contenue_publication.likes;

import com.fsb.PFE.authentication.entity.User;
import com.fsb.PFE.authentication.entity.UserDTO;
import com.fsb.PFE.authentication.entity.UserDTOLikes;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {
    @Autowired
    LikeService likeService;

    @PostMapping("/addLike/{idContenuePub}")
    @Authorization("hasRole('Artiste') or hasRole('User')")
    public void addLike(@PathVariable int idContenuePub){
        likeService.addLike(idContenuePub);
    }

    @DeleteMapping("/deleteLike/{idContenuePub}")
    @Authorization("hasRole('Artiste') or hasRole('User')")
    public void deleteLike(@PathVariable int idContenuePub){
        likeService.deleteLike(idContenuePub);
    }

    @GetMapping("/getAllLikes/{idContenuPub}")
    @Authorization("hasRole('Artiste') or hasRole('User')")
    public List<Like> getAllLikes(@PathVariable int idContenuPub){
        return likeService.getAllLikes(idContenuPub);
    }

    @GetMapping("/getAllLikesNames/{idContenuPub}")
    @Authorization("hasRole('Artiste') or hasRole('User')")
    public List<UserDTOLikes> getAllLikesNames(@PathVariable int idContenuPub){
        return likeService.getAllLikesNames(idContenuPub);
    }

}
