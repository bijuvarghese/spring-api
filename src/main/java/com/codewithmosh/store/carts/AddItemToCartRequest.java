package com.codewithmosh.store.carts;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class AddItemToCartRequest implements Serializable {
    @NotNull
    private Long productId;
    @NotNull
    private final Integer quantity;
}