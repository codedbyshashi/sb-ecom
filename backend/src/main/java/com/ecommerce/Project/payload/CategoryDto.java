package com.ecommerce.Project.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @Schema(description = "Category ID for a particular category",example = "101")
    private Long categoryId;
    @Schema(description = "Category Name for you wish to create",example = "iphone 16")
    private String categoryName;
}
