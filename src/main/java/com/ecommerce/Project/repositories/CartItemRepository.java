package com.ecommerce.Project.repositories;

import com.ecommerce.Project.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
