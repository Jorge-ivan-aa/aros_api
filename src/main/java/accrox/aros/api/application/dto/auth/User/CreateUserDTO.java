package accrox.aros.api.application.dto.auth.User;

import jakarta.validation.constraints.*;

public record CreateUserDTO(

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        String name,

        @NotBlank(message = "Document is required")
        String document,

        @NotBlank(message = "Email is required")
        @Email(message = "Must be a valid email address")
        String email,

        @NotBlank(message = "Password is required")
        //@Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        @NotBlank(message = "Address is required")
        String address,

        //@Pattern(regexp = "\\d{10}", message = "Phone number must contain exactly 10 digits")
        String phone

)  {

}