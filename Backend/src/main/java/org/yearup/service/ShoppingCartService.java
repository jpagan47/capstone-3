package org.yearup.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ProductRepository;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;


@Service
public class ShoppingCartService {
    // Builds shopping carts from saved cart items and product details
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService, ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    public ShoppingCart getByUserId(int userId) {
        ShoppingCart cart = new ShoppingCart();
        //Convert each saved cart row into a full cart item
        List<CartItem> items = shoppingCartRepository.getByUserId(userId);
        for (CartItem item : items) {
            Product product = productService.getById(item.getProductId());
            ShoppingCartItem cartItem = new ShoppingCartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(item.getQuantity());
            cart.add(cartItem);
        }
        return cart;
    }

    // Add the product or increase the quantity if it is already in the cart
    public ShoppingCart addToCart(int userId, int productId) {
        CartItem product =  new CartItem();
        product.setProductId(productId);
        product.setUserId(userId);

        CartItem item = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (item == null) {
           shoppingCartRepository.save(product);
        } else {
            item.setQuantity((item.getQuantity() + 1));
            shoppingCartRepository.save(item);
        }
        return getByUserId(userId);
    }

    // Remove all saved cart items for the user
    @Transactional
    public void clearCart(int userId) {
        shoppingCartRepository.deleteByUserId(userId);
    }

    //Update the quantity for a specific product in the user's cart
    public ShoppingCart updateQuantity(int userId, int productId, int quantity) {
        CartItem item = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        item.setQuantity(quantity);
        shoppingCartRepository.save(item);
        return getByUserId(userId);
    }

    //Build a cart from the user's saved cart items
    public ShoppingCart getCart(int id) {
        ShoppingCart cart = new ShoppingCart();
        List<CartItem> items = shoppingCartRepository.findByUserId(id);

        for (CartItem item : items) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setProduct(product);
            shoppingCartItem.setQuantity(item.getQuantity());
            cart.add(shoppingCartItem);
        }
        return cart;
    }
}
