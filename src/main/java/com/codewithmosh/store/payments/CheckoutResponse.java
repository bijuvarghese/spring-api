package com.codewithmosh.store.payments;

import lombok.*;

@Data
@AllArgsConstructor
public class CheckoutResponse {
    private Long orderId;
    private String checkoutUrl;
}
