package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.security.Principal;

// Handles shopping cart operations for authenticated users

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    // Service used to manage users and shopping carts
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    // Return the current user's shopping cart
    @GetMapping
    public ResponseEntity<ShoppingCart> getCart(Principal principal) {
        // Ensure the request is made by an authenticated user
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userName = principal.getName();
        // Look up the current user's account
        User user = userService.getByUserName(userName);
        int userId = user.getId();
        ShoppingCart cart = shoppingCartService.getByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    // Add a product to the current user's cart
    @PostMapping("/products/{id}")
    public ResponseEntity<ShoppingCart> addToCart(@PathVariable int id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = principal.getName();
        User user = userService.getByUserName(username);
        int userId = user.getId();
        ShoppingCart cart = shoppingCartService.addToCart(userId,id);
        // Return the updated cart after the item is added
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    // Update the quantitty of an item in the current user's cart

    @PutMapping("/products/{id}")
    public ResponseEntity<ShoppingCart> updateCartItem(@PathVariable int id, @RequestBody ShoppingCartItem item, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userService.getByUserName(principal.getName());
        ShoppingCart cart = shoppingCartService.updateQuantity(user.getId(), id, item.getQuantity());
        return ResponseEntity.ok(cart);
    }


    // Remove all items from the current user's cart
    @DeleteMapping
    public ResponseEntity<ShoppingCart> deleteCart(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userService.getByUserName(principal.getName());
        shoppingCartService.clearCart(user.getId());
        ShoppingCart cart = shoppingCartService.getByUserId(user.getId());
        // Return a successful response after clearing the cart
        return ResponseEntity.status(HttpStatus.OK).body(cart);

    }


}
