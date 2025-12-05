package com.ecommerce.Project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne(mappedBy = "payment",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Order order;

    @NotBlank
    @Size(min = 4,message = "Payment method must contain atleast 4 characters")
    private String paymentMethod;

    private String pgPaymentId;
    private String pgStatus;
    private String pgResponseMessage;
    private String pgName;

    public Payment(String paymentMethod,String pgPaymentId,String pgResponseMessage,String pgName,String pgStatus) {
        this.paymentMethod = paymentMethod;
        this.pgPaymentId = pgPaymentId;
        this.pgResponseMessage = pgResponseMessage;
        this.pgName = pgName;
        this.pgStatus = pgStatus;

    }
}
