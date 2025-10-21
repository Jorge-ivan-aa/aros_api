package accrox.aros.api.infrastructure.spring.dto;

import accrox.aros.api.application.dto.area.GetAreaInput;
import accrox.aros.api.application.dto.user.CreateUserInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collection;

public record CreateUserRequest (

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
        String phone,

        Collection<GetAreaInput>areas

){
        /**
         * transform request into a valid input
         *
         * @return input
         */
        public CreateUserInput toInput() {
                return new CreateUserInput(
                        this.name,
                        this.document,
                        this.email,
                        this.password,
                        this.address,
                        this.phone,
                        this.areas
                );
        }
}
