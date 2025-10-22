package accrox.aros.api.application.dto.user;

import accrox.aros.api.application.dto.area.GetAreaInput;
import java.util.Collection;

public record UpdateUserAreaInput(
    String document,
    Collection<GetAreaInput> areas
) {}
