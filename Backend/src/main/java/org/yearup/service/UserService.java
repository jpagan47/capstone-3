package org.yearup.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.yearup.models.User;
import org.yearup.repository.UserRepository;

import java.util.List;

@Service
public class UserService
{
    //Constructor
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    // Return all registered users
    public List<User> getAll()
    {
        return userRepository.findAll();
    }

    // Look up a user by their username
    public User getByUserName(String username)
    {
        return userRepository.findByUsername(username);
    }

    //Return the user's ID, or -1 if the user does not exist
    public int getIdByUsername(String username)
    {
        User user = userRepository.findByUsername(username);
        return user != null ? user.getId() : -1;
    }

    //  Check whether a username is already in use
    public boolean exists(String username)
    {
        return userRepository.existsByUsername(username);
    }

    //Encrypting the password
    public User create(User user)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


}
