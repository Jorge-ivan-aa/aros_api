package accrox.aros.api.application.dto.user;

import java.util.Collection;

public record UpdateUserInput(
        String document,

        String name,

        String email,

        String password,

        String address,

        String phone,

        Collection<Long> areas) {
}
