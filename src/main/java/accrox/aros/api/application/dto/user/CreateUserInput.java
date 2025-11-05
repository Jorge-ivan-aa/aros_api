package accrox.aros.api.application.dto.user;

import java.util.Collection;

public record CreateUserInput(
    String name,
    String document,
    String email,
    String password,
    String address,
    String phone,
    Collection<Long> areas
) {}
