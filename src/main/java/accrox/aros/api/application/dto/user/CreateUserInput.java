package accrox.aros.api.application.dto.user;

import accrox.aros.api.application.dto.area.GetAreaInput;
import java.util.Collection;

public record CreateUserInput(
    String name,
    String document,
    String email,
    String password,
    String address,
    String phone,
    Collection<GetAreaInput> areas
) {}
