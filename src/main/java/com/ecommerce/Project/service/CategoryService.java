package com.ecommerce.Project.service;

import com.ecommerce.Project.model.Category;
import com.ecommerce.Project.payload.CategoryDto;
import com.ecommerce.Project.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
    CategoryDto createCategory(CategoryDto categoryDto);


    String deleteCategory(Long categoryId);

    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
}
