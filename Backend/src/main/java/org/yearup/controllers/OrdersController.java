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

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final UserService userService;
    private final ShoppingCartService shoppingCartServices;
    private final OrderService orderService;

    public OrdersController(UserService userService, ShoppingCartService shoppingCartServices, OrderService orderService) {
        this.userService = userService;
        this.shoppingCartServices = shoppingCartServices;
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<ShoppingCart> checkoutCart(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        int userId = userService.getIdByUsername(principal.getName());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
