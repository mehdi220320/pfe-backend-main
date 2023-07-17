package com.fsb.PFE.UtilisateurSimple;

import com.fsb.PFE.Artiste.Artiste;
import com.fsb.PFE.Artiste.FollowedArtistDTO;
import com.fsb.PFE.shared.publication.Publication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/utilisateurSimple")
public class UtilisateurSimpleControler {
    private final UtilisateurService service;

    public UtilisateurSimpleControler(UtilisateurImpService utilisateurImpService) {
        this.service = (UtilisateurService) utilisateurImpService;
    }
    @PostMapping("/adduser")
    public UtilisateurSimple add(@RequestBody UtilisateurSimple user){service.add(user);
        return user;
    }

    @PostMapping("/saveuser")
    // convert the coming code from front to int
    public void saveArtiste(@RequestBody UserVerificationDTO userVerif) {
        service.saveUtilisateur(userVerif.getUser(),userVerif.getCode());
    }

    @GetMapping("/getall")
    public List<UtilisateurSimple> getAll(){
        return service.getAll();
    }

    @GetMapping("/get/{userName}")
    public UtilisateurSimple getbyId(@PathVariable(name = "userName") String userName){
        return service.getById(userName);
    }

    @PutMapping("/update")
    public UtilisateurSimple update(@RequestBody UtilisateurSimple user){
        return service.update(user);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id")String id){service.delete(id);}


    @PutMapping("/resendcode")
    public void resendCode(@RequestBody String utilisateurId) {
        System.out.println("/////////////////////////");
        service.resendCode(utilisateurId);
    }

    @GetMapping("/getfollowing")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public Set<FollowedArtistDTO> getAllFollowing(){
        return service.getAllFollowing();
    }

    @GetMapping("/getfollowers")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public Set<FollowedArtistDTO> getAllFollowers(){
        return service.getAllFollowers();
    }

    @DeleteMapping("/unfollow/{profileId}")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public void unfollow(@PathVariable String profileId){
        service.unfollow(profileId);
    }

    @GetMapping("/getfollowingposts")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public List<Publication> getFollowingPosts(){
        return service.getFollowingPosts();
    }

    @DeleteMapping("/deletefollower/{profileId}")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public void deleteFollower(@PathVariable String profileId){
        service.deleteFollower(profileId);
    }

}
