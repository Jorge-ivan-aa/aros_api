package accrox.aros.api.application.dto.auth;

import java.util.Collection;

import accrox.aros.api.application.dto.area.GetAreaOuput;

public record AuthDetailsOutput(
        String name,
        String document,
        Collection<GetAreaOuput> areas) {
}