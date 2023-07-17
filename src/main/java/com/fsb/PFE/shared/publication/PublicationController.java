package com.fsb.PFE.shared.publication;


import com.fsb.PFE.shared.contenue_publication.ContenuePub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/publication")
public class PublicationController {
    @Autowired PublicationService  publicationService;

    @PostMapping("/addPub")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public void addPublication(@RequestBody Publication publication){
        publicationService.addPublication(publication);
    }


    @GetMapping("/getAllPub")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public List<PublicationDTO> getAllPublications(){
        return publicationService.getAllPublications();
    }

    @GetMapping("/getPubById")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public PublicationDTO getPublicationById(@RequestBody Map<String, Object> requestBody){
        int id = (int) requestBody.get("id");
        return publicationService.getPublicationById(id);
    }

    @DeleteMapping("/deletePub")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public void deletePublication(@RequestBody Map<String, Object> requestBody){
        int id = (int) requestBody.get("id");
        publicationService.deletePublication(id);
    }

    @GetMapping("/getPubByOwner")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public List<PublicationDTO> getPublicationsByOwner(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return publicationService.getPublicationsByOwner(auth.getName());
    }

    @GetMapping("/getPubByProfileId/{profileId}")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public List<PublicationDTO> getPublicationsByProfileId(@PathVariable String profileId){
        return publicationService.getPublicationsByProfileId(profileId);
    }




}
