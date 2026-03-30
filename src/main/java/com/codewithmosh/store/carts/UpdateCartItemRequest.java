package com.codewithmosh.store.carts;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequest {
    @NotNull(message = "Quantity required")
    @Min(value = 1, message = "Min 1 value")
    private Integer quantity;
}
