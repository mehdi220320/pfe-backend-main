package com.fsb.PFE.Artiste;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/artiste")
public class ArtisteController {

    private final ArtisteService artisteService;

    public ArtisteController(ArtisteServiceImpl artisteService) {
        this.artisteService = (ArtisteService) artisteService;
    }



    @PostMapping("/addartiste")
    public Artiste addArtiste(@RequestBody Artiste artiste) {
        return artisteService.addArtiste(artiste);
    }

    @PostMapping("/saveartiste")
    // convert the coming code from front to int
    public void saveArtiste(@RequestBody ArtisteVerificationDTO artisteVerificationDTO) {
       artisteService.saveArtiste(artisteVerificationDTO.getArtiste(),artisteVerificationDTO.getCode());
    }

    @PutMapping("/resendcode")
    public void resendCode(@RequestBody String artisteId) {
        System.out.println("/////////////////////////");
        artisteService.resendCode(artisteId);
    }







    @PutMapping("updateArtiste")
    public void updateArtiste(@RequestBody Artiste artiste) {
        artisteService.updateArtiste(artiste);
    }

    @GetMapping("/getArtiste/{userName}")
    public Artiste getArtiste(@PathVariable(name = "userName") String userName) {
        return artisteService.getArtiste(userName);
    }

    @GetMapping("/allArtist")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public List<Artiste> getAllOtherArtiste() {
        List<Artiste> l1 = artisteService.getAllArtiste();
        l1.forEach(artiste -> {
            artiste.setFollower(null);
            artiste.setFollowing(null);
        });
        return l1;
    }

    @DeleteMapping("/deleteArtiste/{id}")
    public void deleteArtiste(@PathVariable(name = "id") Integer id) {
        artisteService.deleteArtiste(id);
    }

    @GetMapping("/getFollowing")
    public Following getFollowing() {
        return artisteService.getAllFollowing();
    }

    @PostMapping("/follow/{id}")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public void follow(@PathVariable(name = "id") String id) {
        artisteService.follow(id);
    }

    @DeleteMapping("/unfollow/{profileId}")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public void unfollow(@PathVariable(name = "profileId") String id) {
        artisteService.unfollow(id);
    }


    @GetMapping("/getFollowers")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public Following getFollowers() {
        return artisteService.getAllFollowers();
    }

    @DeleteMapping("/deleteFollower/{profileId}")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public void deleteFollower(@PathVariable(name = "profileId") String id) {
        artisteService.deleteFollower(id);
    }


}
