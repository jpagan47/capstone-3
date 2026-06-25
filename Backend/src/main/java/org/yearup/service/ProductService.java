package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Product;
import org.yearup.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    //Search products using the provided filters
    public List<Product> search(Integer categoryId, Double minPrice, Double maxPrice, String subCategory) {
        List<Product> products = categoryId != null
                ? productRepository.findByCategoryId(categoryId)
                : productRepository.findAll();

        //Apply any optional filters before returning the results

        return products.stream()
                .filter(p -> minPrice == null || p.getPrice() >= minPrice)
                .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
                .filter(p -> subCategory == null || subCategory.equalsIgnoreCase(p.getSubCategory()))
                .toList();
    }

    //Return all products for the selected category

    public List<Product> listByCategoryId(int categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // Return the requested amount if it exists
    public Product getById(int productId) {
        return productRepository.findById(productId).orElse(null);
    }

    // Saving a new product
    public Product create(Product product) {
        //Resetting the ID, to create a new record
        product.setProductId(0);
        return productRepository.save(product);
    }

    // Updating the existing product with the new values
    public Product update(int productId, Product product) {
        Product existing = productRepository.findById(productId).orElseThrow();
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());
        existing.setCategoryId(product.getCategoryId());
        existing.setDescription(product.getDescription());
        existing.setSubCategory(product.getSubCategory());
        existing.setFeatured(product.isFeatured());
        existing.setImageUrl(product.getImageUrl());
        return productRepository.save(existing);
    }
    // Remove the product by its ID
    public void delete(int productId) {
        productRepository.deleteById(productId);
    }
}
