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
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService, ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
        this.productRepository = productRepository;
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
        CartItem product =  new CartItem();
        product.setProductId(productId);
        product.setUserId(userId);

        CartItem item = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (item == null) {
           shoppingCartRepository.save(product);
        } else {
            item.setQuantity((item.getQuantity() + 1));
            shoppingCartRepository.save(product);
        }
        return getByUserId(userId);
    }
    @Transactional
    public void clearCart(int userId) {
//        List<CartItem> items = shoppingCartRepository.findByUserId(userId);
        shoppingCartRepository.deleteByUserId(userId);
    }

    public ShoppingCart updateQuantity(int userId, int productId, int quantity) {
        CartItem item = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        item.setQuantity(quantity);
        shoppingCartRepository.save(item);
        return getByUserId(userId);
    }

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
