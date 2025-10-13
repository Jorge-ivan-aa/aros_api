package accrox.aros.api.application.dto.auth.User;

import jakarta.validation.constraints.*;

public record CreateUserOutput(

        String name,
        String document,
        String email,
        String password,
        String address,
        String phone

)  {

}