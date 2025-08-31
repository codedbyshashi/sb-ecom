package com.ecommerce.Project.service;

import com.ecommerce.Project.model.Product;
import com.ecommerce.Project.payload.ProductDto;
import com.ecommerce.Project.payload.ProductResponse;

public interface ProductService {
    ProductDto addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();
}
