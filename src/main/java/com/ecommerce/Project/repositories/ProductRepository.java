package com.ecommerce.Project.repositories;

import com.ecommerce.Project.model.Category;
import com.ecommerce.Project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryOrderByPriceAsc(Category category);
}
