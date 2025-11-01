package com.ecommerce.Project.controller;

import com.ecommerce.Project.model.Cart;
import com.ecommerce.Project.payload.CartDto;
import com.ecommerce.Project.repositories.CartRepository;
import com.ecommerce.Project.service.CartService;
import com.ecommerce.Project.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    AuthUtil authUtil;

    @Autowired
    private CartService cartService;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable Long productId,
                                                    @PathVariable Integer quantity){
        CartDto cartDto = cartService.addProductToCart(productId,quantity);
        return new ResponseEntity<CartDto>(cartDto, HttpStatus.CREATED);
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartDto>> getCarts(){
        List<CartDto> cartDtos = cartService.getAllCarts();
        return new ResponseEntity<List<CartDto>>(cartDtos, HttpStatus.FOUND);
    }

    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDto> getCartById(){
        String emailId = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(emailId);
        Long cartId = cart.getCartId();
        CartDto cartDto = cartService.getCart(emailId,cartId);
        return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
    }

    @PutMapping("/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDto> updateCartProduct(@PathVariable Long productId,
                                                     @PathVariable String operation){
     CartDto cartDto = cartService.updateProductQuantityInCart(productId,operation.equalsIgnoreCase("delete")?-1:1);
     return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);

    }

    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId,
                                                        @PathVariable Long productId){
    String status = cartService.deleteProductFromCart(cartId,productId);
    return new ResponseEntity<String>(status,HttpStatus.OK);

    }
}
