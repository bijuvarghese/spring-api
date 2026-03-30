package com.codewithmosh.store.carts;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemDto {
    private UUID cartId;
    private BigDecimal totalPrice;
    private Integer quantity;
    private ProductDto product;

}
