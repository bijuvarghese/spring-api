package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthenticationRequest {
    @NotNull(message = "user name is required")
    @NotBlank
    private String email;
    @NotBlank
    @NotNull(message = "password is required")
    private String password;
}
