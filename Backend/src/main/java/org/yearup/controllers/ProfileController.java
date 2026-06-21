package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private ProfileService profileService;
    private UserService userService;

    public ProfileController( ProfileService profileService,UserService userService){
        this.profileService = profileService;
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<Profile> getProfile(Principal principal){
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userService.getByUserName(principal.getName());
        Profile profile = profileService.getByUserId(user.getId());

        return ResponseEntity.ok(profile);
    }

}
