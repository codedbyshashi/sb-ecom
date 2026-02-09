package com.ecommerce.Project.service;

import com.ecommerce.Project.payload.OrderDto;
import jakarta.transaction.Transactional;

public interface OrderService {
    @Transactional
    OrderDto placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage);
}
