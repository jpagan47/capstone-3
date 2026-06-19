package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.controllers.ShoppingCartController;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId)
    {
        // load the user's cart rows, look up each product, and build the ShoppingCart
        ShoppingCart cart = new ShoppingCart();
        List<ShoppingCartItem> items = shoppingCartRepository.getByUserId(userId);
        for (ShoppingCartItem item : items){
            Product product = productService.getById(item.getProductId());
            item.setProduct(product);
            cart.add(item);
        }
        return cart;
    }

    // add additional methods here
}
