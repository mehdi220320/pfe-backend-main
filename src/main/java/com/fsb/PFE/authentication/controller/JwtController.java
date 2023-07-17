package com.fsb.PFE.authentication.controller;

import com.fsb.PFE.authentication.entity.JwtRequest;
import com.fsb.PFE.authentication.entity.JwtResponse;
import com.fsb.PFE.authentication.service.JwtService;
import com.fsb.PFE.authentication.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {
    @Autowired
    protected JwtService jwtService;
    @Autowired
    private LogoutService logoutService;

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println(jwtRequest);
        return jwtService.createJwtToken(jwtRequest);
    }

    @PostMapping("/logouttt")
    public ResponseEntity<?> logout() {
        logoutService.logout();
        return ResponseEntity.ok().build();
    }



}
