package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.lang.annotation.Native;
import java.util.UUID;

@Data
public class CheckOutRequest {
    @NotNull(message = "cartid is required")
    private UUID cartId;
}
