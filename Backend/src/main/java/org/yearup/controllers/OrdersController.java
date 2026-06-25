package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.models.ShoppingCart;
import org.yearup.service.OrderService;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.security.Principal;

//Handles checkout request for the current user's cart

@RestController
@RequestMapping("/orders")


public class OrdersController {
    private final UserService userService;
    private final ShoppingCartService shoppingCartServices;
    private final OrderService orderService;

    //Inject the services needed to process an order

    public OrdersController(UserService userService, ShoppingCartService shoppingCartServices, OrderService orderService) {
        this.userService = userService;
        this.shoppingCartServices = shoppingCartServices;
        this.orderService = orderService;
    }

    //Validate the signed-in user before starting checkout

    @GetMapping
    public ResponseEntity<ShoppingCart> checkoutCart(Principal principal) {
       // Reject checkout request from unauthorized users
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        int userId = userService.getIdByUsername(principal.getName());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
