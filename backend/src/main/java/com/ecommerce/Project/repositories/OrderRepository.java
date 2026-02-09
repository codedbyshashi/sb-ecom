package com.ecommerce.Project.repositories;

import com.ecommerce.Project.model.Order;
import com.ecommerce.Project.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
