package org.yearup.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping
public class OrdersController {
    @PostMapping
    public ResponseEntity<Void> createOrder(Principal principal){
        return ResponseEntity.ok().build();
    }
}
