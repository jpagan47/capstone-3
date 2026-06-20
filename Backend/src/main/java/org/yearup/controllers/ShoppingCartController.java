package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.security.Principal;

// convert this class to a REST controller
// only logged-in users should have access to these actions
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    // a shopping cart controller depends on the service layer
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    // each method in this controller requires a Principal object as a parameter
    @GetMapping
    public ResponseEntity<ShoppingCart> getCart(Principal principal) {
        // get the currently logged-in username
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userName = principal.getName();
        // find database user by username
        User user = userService.getByUserName(userName);
        int userId = user.getId();
        ShoppingCart cart = shoppingCartService.getByUserId(userId);
        // use the shoppingCartService to get all items in the cart and return the cart
        return ResponseEntity.ok(cart);
    }

    // add a POST method to add a product to the cart - the url should be
    @PostMapping("/products/{id}")
    // https://localhost:8080/cart/products/15  (15 is the productId to be added)
    public ResponseEntity<ShoppingCart> addToCart(@PathVariable int id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = principal.getName();
        User user = userService.getByUserName(username);
        int userId = user.getId();
        ShoppingCart cart = shoppingCartService.getByUserId(userId);
        // return the updated cart with status 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }


    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated; return the cart (200 OK)


    // add a DELETE method to clear all products from the current users cart
    //Todo fix this endpoint
    @DeleteMapping
    public ResponseEntity<ShoppingCart> deleteCart(Principal principal) {
        User user = userService.getByUserName(principal.getName());
        shoppingCartService.clearCart(user.getId());
        ShoppingCart cart = shoppingCartService.getCart(user.getId());
        return ResponseEntity.ok(cart);

    }

    // https://localhost:8080/cart  - return the (now empty) cart so the front end can refresh it (200 OK)

}
