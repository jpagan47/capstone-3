package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService {
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId) {
        // load the user's cart rows, look up each product, and build the ShoppingCart
        ShoppingCart cart = new ShoppingCart();
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

    // add additional methods here
    public ShoppingCart addToCart(int userId, int productId) {
        CartItem item = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        if (item == null) {
            item = new CartItem();
            item.setUserId(userId);
            item.setProductId(productId);
            item.setQuantity(1);
        } else {
            item.setQuantity((item.getQuantity() + 1));
        }
        shoppingCartRepository.save(item);
        return getByUserId(userId);
    }

    public void clearCart(int userId) {
        List<CartItem> items = shoppingCartRepository.findByUserId(userId);
        for (CartItem item : items) {
            shoppingCartRepository.delete(item);
        }
    }

    public ShoppingCart getCart(int id) {
        return null;
    }

    public ShoppingCart updateQuantity(int userId, int productId, int quantity) {
        CartItem item = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        item.setQuantity(quantity);
        shoppingCartRepository.save(item);
        return getByUserId(userId);
    }
}
