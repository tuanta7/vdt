package com.vdt.fosho.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@RequiredArgsConstructor
public class LoginDTO {
    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotBlank
    @Length(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
