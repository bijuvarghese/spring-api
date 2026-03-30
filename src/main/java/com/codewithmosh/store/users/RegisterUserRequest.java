package com.codewithmosh.store.users;

import com.codewithmosh.store.validations.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;
    @NotBlank(message = "need email")
    @Email(message = "not a valid email")
    @Lowercase(message = "email must be lowercase")
    private String email;
    @Size(min = 6, message = "More than 6 char")
    private String password;
}
