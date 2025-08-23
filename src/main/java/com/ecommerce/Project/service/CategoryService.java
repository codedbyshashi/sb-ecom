package com.ecommerce.Project.service;

import com.ecommerce.Project.model.Category;
import com.ecommerce.Project.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
    void createCategory(Category category);


    String deleteCategory(Long categoryId);

    Category updateCategory(Category category, Long categoryId);
}
