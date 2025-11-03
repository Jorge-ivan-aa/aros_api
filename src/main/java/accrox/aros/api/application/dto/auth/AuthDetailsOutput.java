package accrox.aros.api.application.dto.auth;

import java.util.Collection;

import accrox.aros.api.application.dto.area.GetAreaOutput;

public record AuthDetailsOutput(
        String name,
        String document,
        Collection<GetAreaOutput> areas) {
}