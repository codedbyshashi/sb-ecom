package com.ecommerce.Project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private Long addressId;
    private String paymentMethod;
    private String pgName;
    private String pgPaymentId;
    private String pgStatus;
    private String pgResponseMessage;
}
