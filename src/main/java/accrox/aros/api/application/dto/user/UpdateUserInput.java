package accrox.aros.api.application.dto.user;

public record UpdateUserInput(
    String document,

    String name,

    String email,

    String password,

    String address,

    String phone
) {}
