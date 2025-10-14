package accrox.aros.api.application.dto.user;

public record CreateUserInput (
        String name,
        String document,
        String email,
        String password,
        String address,
        String phone

) {
}
