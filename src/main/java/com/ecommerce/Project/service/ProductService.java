package com.ecommerce.Project.service;

import com.ecommerce.Project.model.Product;
import com.ecommerce.Project.payload.ProductDto;

public interface ProductService {
    ProductDto addProduct(Long categoryId, Product product);
}
