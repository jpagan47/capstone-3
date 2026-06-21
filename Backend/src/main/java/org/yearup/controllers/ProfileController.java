package org.yearup.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private ProfileService profileService;
    private UserService userService;


}
