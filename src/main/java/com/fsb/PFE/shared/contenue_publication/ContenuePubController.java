package com.fsb.PFE.shared.contenue_publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contenuePub")
public class ContenuePubController {
    @Autowired ContenuePubService contenuePubService;

    @PostMapping("/addContenuePub")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public ContenuePub addContenuePub(@RequestBody ContenuePub contenuePub) {
        return contenuePubService.addContenuePub(contenuePub);
    }

    @PutMapping("/updateContenuePub")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public ContenuePub updateContenuePub(@RequestBody ContenuePub contenuePub) {
        return contenuePubService.updateContenuePub(contenuePub);
    }


    @GetMapping("/getContenuePubById")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public ContenuePub getContenuePubById(@RequestBody int id) {
        return contenuePubService.getContenuePubById(id);
    }

}
