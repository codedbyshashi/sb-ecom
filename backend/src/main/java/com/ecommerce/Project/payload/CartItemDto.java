package com.ecommerce.Project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long cartItemId;
    private CartDto cartDto;
    private ProductDto productDto;
    private Integer quantity;
    private Double discount;
    private Double productPrice;

}
