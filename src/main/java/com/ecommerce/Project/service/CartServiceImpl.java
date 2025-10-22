package com.ecommerce.Project.service;

import com.ecommerce.Project.exceptions.APIException;
import com.ecommerce.Project.exceptions.ResourceNotFoundException;
import com.ecommerce.Project.model.Cart;
import com.ecommerce.Project.model.CartItem;
import com.ecommerce.Project.model.Product;
import com.ecommerce.Project.payload.CartDto;
import com.ecommerce.Project.payload.ProductDto;
import com.ecommerce.Project.repositories.CartItemRepository;
import com.ecommerce.Project.repositories.CartRepository;
import com.ecommerce.Project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    AuthUtil authUtil;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto addProductToCart(Long productId, Integer quantity) {
        // Find existing cart or create one
        Cart cart = createCart();
        // Retrieve Product Details
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));
        // Perform Validations
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(
                cart.getCartId(),
                productId
        );

        if(cartItem!=null){
            throw new APIException("Product "+product.getProductName()+ "already exists in the cart");
        }
        if(product.getQuantity()==0){
            throw new APIException(product.getProductName()+ " is not available");
        }

        if(product.getQuantity()<quantity){
            throw new APIException("Please, make an order of the "+product.getProductName()
                    +" less than or equal to quantity "+product.getQuantity()+".");
        }
        // Create cart item
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());
        // save Cart item
        cartItemRepository.save(newCartItem);
        product.setQuantity(product.getQuantity());

        cart.setTotalPrice(cart.getTotalPrice()+(product.getSpecialPrice()*quantity));

        cartRepository.save(cart);

        CartDto cartDto = modelMapper.map(cart,CartDto.class);

        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDto> productDtoStream = cartItems.stream().map(item->{
            ProductDto map = modelMapper.map(item.getProduct(),ProductDto.class);
            map.setQuantity(item.getQuantity());
            return map;
        });

        cartDto.setProducts(productDtoStream.toList());
        // Return updated cart
        return cartDto;
    }

    private Cart createCart(){
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if(userCart!=null){
            return userCart;
        }

        Cart cart = new Cart();
        cart.setTotalPrice(0.00);
        cart.setUser(authUtil.loggedInUser());
        Cart newCart = cartRepository.save(cart);
        return newCart;
    }
}
