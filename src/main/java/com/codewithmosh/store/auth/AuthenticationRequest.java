package com.codewithmosh.store.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthenticationRequest {
    @NotNull(message = "user name is required")
    @NotBlank
    private String email;
    @NotBlank
    @NotNull(message = "password is required")
    private String password;
}
