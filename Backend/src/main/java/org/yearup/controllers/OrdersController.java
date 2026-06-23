package org.yearup.controllers;


import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;
import org.yearup.service.OrderService;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private UserService userService;
    private ShoppingCartService shoppingCartServices;
    private OrderService orderService;

    public OrdersController(UserService userService, ShoppingCartService shoppingCartServices, OrderService orderService) {
        this.userService = userService;
        this.shoppingCartServices = shoppingCartServices;
        this.orderService = orderService;
    }


//    @PostMapping
//    public ResponseEntity<Void> createOrder(Principal principal) {
//        User user = userService.getByUserName((principal.getName()));
//        ShoppingCart cart = shoppingCartServices.getCart(user.getId());
//        if (cart.getItems().isEmpty()) {
//            return ResponseEntity.badRequest().body();
//        }
//        Order order = orderService.createOrder(user.getId(),cart);
//        shoppingCartServices.clearCart(user.getId());
//        return ResponseEntity.ok().build();
//    }
//        return null;
//    }
    @GetMapping
    public ResponseEntity<ShoppingCart> checkoutCart (Principal principal){
        if(principal == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        int userId = userService.getIdByUsername(principal.getName());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
