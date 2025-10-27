package com.ecommerce.Project.service;

import com.ecommerce.Project.payload.CartDto;

import java.util.List;

public interface CartService {
    CartDto addProductToCart(Long productId, Integer quantity);

    List<CartDto> getAllCarts();

    CartDto getCart(String emailId, Long cartId);
}
