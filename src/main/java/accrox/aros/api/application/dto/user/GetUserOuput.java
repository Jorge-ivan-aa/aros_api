package accrox.aros.api.application.dto.user;

import accrox.aros.api.application.dto.area.GetAreaOutput;

import java.util.Collection;

public record GetUserOuput(
        String name,
        String document,
        String email,
        String phone,
        String address,
        Collection<GetAreaOutput> areas) {
}
