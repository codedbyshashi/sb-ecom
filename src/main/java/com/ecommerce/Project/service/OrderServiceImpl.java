package com.ecommerce.Project.service;

import com.ecommerce.Project.exceptions.APIException;
import com.ecommerce.Project.exceptions.ResourceNotFoundException;
import com.ecommerce.Project.model.*;
import com.ecommerce.Project.payload.OrderDto;
import com.ecommerce.Project.payload.OrderItemDto;
import com.ecommerce.Project.payload.ProductDto;
import com.ecommerce.Project.repositories.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderDto placeOrder(String emailId, Long addressId, String paymentMethod,
                               String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage) {

        // 1. Get cart
        Cart cart = cartRepository.findCartByEmail(emailId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart", "email", emailId);
        }

        // 2. Get address
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        // 3. Create order
        Order order = new Order();
        order.setEmail(emailId);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Order Accepted !");
        order.setAddress(address);

        // 4. FIXED payment constructor order
        Payment payment = new Payment(
                paymentMethod,
                pgPaymentId,
                pgResponseMessage,  // correct order
                pgName,
                pgStatus
        );

        payment.setOrder(order);
        paymentRepository.save(payment);
        order.setPayment(payment);

        Order savedOrder = orderRepository.save(order);

        // 5. Prepare order items from cart items
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new APIException("Cart is empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setOrderedProductPrice(cartItem.getProductPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        orderItems = orderItemRepository.saveAll(orderItems);

        // 6. Update stock and clear cart
        cartItems.forEach(item -> {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);

            cartService.deleteProductFromCart(cart.getCartId(), product.getProductId());
        });

        // 7. Build response DTO
        OrderDto orderDto = modelMapper.map(savedOrder, OrderDto.class);
        orderDto.setOrderItems(new ArrayList<>());

        for (OrderItem item : orderItems) {
            OrderItemDto orderItemDto = modelMapper.map(item, OrderItemDto.class);

            // THE FIX 🔥 productDto was always null because ModelMapper does not map nested product automatically
            ProductDto productDto = modelMapper.map(item.getProduct(), ProductDto.class);
            orderItemDto.setProduct(productDto);

            orderDto.getOrderItems().add(orderItemDto);
        }

        orderDto.setAddressId(addressId);

        return orderDto;
    }
}
