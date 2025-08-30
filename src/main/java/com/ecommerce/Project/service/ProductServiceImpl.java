package com.ecommerce.Project.service;

import com.ecommerce.Project.exceptions.ResourceNotFoundException;
import com.ecommerce.Project.model.Category;
import com.ecommerce.Project.model.Product;
import com.ecommerce.Project.payload.ProductDto;
import com.ecommerce.Project.repositories.CategoryRepository;
import com.ecommerce.Project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto addProduct(Long categoryId, Product product) {
        Category category =  categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Category","categoryId",categoryId));
        product.setImage("default.png");
        product.setCategory(category);
        double specialPrice = product.getPrice() -
                (product.getDiscount() * 0.01)*product.getPrice();
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }
}
