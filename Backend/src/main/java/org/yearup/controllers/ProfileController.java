package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final UserService userService;

    //Inject the services used to manage user profiles

    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    // Return the profile for the signed-in user

    @GetMapping
    public ResponseEntity<Profile> getProfile(Principal principal) {
        // Reject request from unauthenticated users
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userService.getByUserName(principal.getName());
        Profile profile = profileService.getByUserId(user.getId());

        return ResponseEntity.ok(profile);
    }

    // Update the signed-in user's profile

    @PutMapping
    public ResponseEntity<Profile> updateProfile(Principal principal, @RequestBody Profile profile) {
        // Reject request from unauthenticated users
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userService.getByUserName(principal.getName());

        // Save and return the updated profile

        Profile updateProfile = profileService.update(user.getId(), profile);
        return ResponseEntity.ok(updateProfile);
    }


}
