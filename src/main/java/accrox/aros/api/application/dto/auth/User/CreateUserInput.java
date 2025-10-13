package accrox.aros.api.application.dto.auth.User;

public record CreateUserInput (
        String name,
        String document,
        String email,
        String password,
        String address,
        String phone

) {
}
