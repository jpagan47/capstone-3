package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.util.List;

//Inject the required services
@RestController
@RequestMapping("/categories")
@CrossOrigin
public class CategoriesController {
    private final CategoryService categoryService;
    private final ProductService productService;

    // Created an Autowired constructor to inject the categoryService and productService
    @Autowired
    public CategoriesController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }


    //End-point to retrieve all categories
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> category = categoryService.getAllCategories();
        return ResponseEntity.ok(category);
    }

    //End-point to retrieve if the categorie exist
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable int id) {
        // get the category by id
        Category category = categoryService.getById(id);
        if (category == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(category);
    }

    //Returns all the products belonging to that categorie
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<Product>> getProductsById(@PathVariable int categoryId) {
        // get a list of product by categoryId
        List<Product> products = productService.listByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    // Creating a category only if you are an ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        // insert the category and return it with status 201 Created
        Category created = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    // Updating a category only if you are an ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category category) {
        // update the category by id and return the updated category (200 OK)
        Category updated = categoryService.update(id, category);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // Deleting a category only as an ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        // delete the category by id and return status 204 No Content
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
