package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Category;
import org.yearup.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Retrieve all available categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    //Return the matching category, if it exists
    public Category getById(int categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    //Save a new category
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    //Apply updates to an existing category
    public Category update(int categoryId, Category category) {
        // update category and return the updated category
        Category existing = categoryRepository.findById(categoryId).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        return categoryRepository.save(existing);
    }
    //Remove the category by ID
    public void delete(int categoryId) {
        categoryRepository.deleteById(categoryId);

    }
}
