package com.ecommerce.Project.controller;

import com.ecommerce.Project.payload.OrderDto;
import com.ecommerce.Project.payload.OrderRequestDto;
import com.ecommerce.Project.service.OrderService;
import com.ecommerce.Project.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDto> orderProducts(@PathVariable String paymentMethod,
                                                  @RequestBody OrderRequestDto orderRequestDto) {
        String emailId  = authUtil.loggedInEmail();

        OrderDto orderDto = orderService.placeOrder(
                            emailId,
                            orderRequestDto.getAddressId(),
                            paymentMethod,
                            orderRequestDto.getPgName(),
                            orderRequestDto.getPgPaymentId(),
                            orderRequestDto.getPgStatus(),
                            orderRequestDto.getPgResponseMessage()
                    );

        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);


    }
}
