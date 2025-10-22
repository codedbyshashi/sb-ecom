package com.ecommerce.Project.service;

import com.ecommerce.Project.payload.CartDto;

public interface CartService {
    CartDto addProductToCart(Long productId, Integer quantity);
}
