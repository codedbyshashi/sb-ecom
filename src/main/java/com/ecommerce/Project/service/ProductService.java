package com.ecommerce.Project.service;

import com.ecommerce.Project.payload.ProductDto;
import com.ecommerce.Project.payload.ProductResponse;

public interface ProductService {
    ProductDto addProduct(Long categoryId, ProductDto product);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDto updateProduct(Long productId, ProductDto product);

    ProductDto deleteProduct(Long productId);
}
