package com.fsb.PFE.authentication.controller;

import com.fsb.PFE.authentication.dao.UserDao;
import com.fsb.PFE.authentication.entity.User;
import com.fsb.PFE.authentication.service.UserService;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired private UserDao userDao;


    @PostConstruct
    public void initRolesAndUsers() {
        userService.initRolesAndUser();
    }



    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        System.out.println(user);
        return userService.registerNewUser(user);
    }

    @GetMapping({"/forSimpleUser"})
    @PreAuthorize("hasRole('User')")
    public String forAdmin() {
        return "this URL is only accessible to User";
    }

    @GetMapping({"/forArtist"})
    @PreAuthorize("hasRole('Artiste')")
    public String forUser() {
        return "this URL is only accessible to Artiste";
    }


    @GetMapping({"/fetchUserByProfileId/{profileId}"})
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public User fetchUserByProfileId(@PathVariable String profileId) {
        User u = userService.fetchUserByProfileId(profileId);
        return getUserNoFollow(u);
    }


    @GetMapping({"/fetchCurrentUser/{profileId}"})
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public User fetchCurrentUser(@PathVariable String profileId) {
        User u =  userService.fetchCurrentUser(profileId);
        return getUserNoFollow(u);
    }

    @PostMapping({"/follow/{profileId}"})
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public void follow(@PathVariable String profileId) {
        userService.followUser(profileId);
    }

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable String id){
        User user = userDao.findById(id).get();
        return getUserNoFollow(user);
    }

    private User getUserNoFollow(User u) {
        u.getFollowing().forEach(followUser -> {
            followUser.setFollower(null);
            followUser.setFollowing(null);
        });
        u.getFollower().forEach(followUser -> {
            followUser.setFollowing(null);
            followUser.setFollower(null);
        });
        return u;
    }





}
