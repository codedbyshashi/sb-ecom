package com.ecommerce.Project.service;

import com.ecommerce.Project.exceptions.ResourceNotFoundException;
import com.ecommerce.Project.model.Category;
import com.ecommerce.Project.model.Product;
import com.ecommerce.Project.payload.ProductDto;
import com.ecommerce.Project.payload.ProductResponse;
import com.ecommerce.Project.repositories.CategoryRepository;
import com.ecommerce.Project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto addProduct(Long categoryId, ProductDto productDto) {
        Category category =  categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Category","categoryId",categoryId));
        Product product = modelMapper.map(productDto,Product.class);
        product.setImage("default.png");
        product.setCategory(category);
        double specialPrice = product.getPrice() -
                (product.getDiscount() * 0.01)*product.getPrice();
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtos);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category =  categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Category","categoryId",categoryId));

        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);

        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtos);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%');

        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtos);
        return productResponse;
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        //Get the existing product from the db
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("product","productId",productId));
        Product product = modelMapper.map(productDto,Product.class);
        //update the product info with the one in request body
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setSpecialPrice(product.getSpecialPrice());
        //save to database
        Product savedProduct = productRepository.save(productFromDb);

        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductDto deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("product","productId",productId));
        productRepository.delete(product);
        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException {
        //Get the product from the db
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("product","productId",productId));
        //upload the image to the server
        //Get the file name of the updated image
        String path = "images/";
        String fileName = uploadImage(path,image);
        //updating the new file name to the product
        productFromDb.setImage(fileName);
        //save the updated product
        Product updatedProduct =  productRepository.save(productFromDb);
        // return dto after mapping product to dto
        return modelMapper.map(updatedProduct,ProductDto.class);
    }

    private String uploadImage(String path, MultipartFile file) throws IOException {
        // file names of current / original file
        String originalFilename = file.getOriginalFilename();

        //Generate a unique file name
        String randomId = UUID.randomUUID().toString();
        // mat.jpg--->1234-->1234.jpg
        String fileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf('.')));
        String filePath = path+ File.separator+fileName;

        // Check if path exist and create
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }

        //upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        // returning file name
        return fileName;
    }
}
