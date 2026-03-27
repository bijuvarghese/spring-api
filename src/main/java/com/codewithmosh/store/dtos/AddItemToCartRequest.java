package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class AddItemToCartRequest implements Serializable {
    @NotNull
    private Long productId;
    @NotNull
    private final Integer quantity;
}