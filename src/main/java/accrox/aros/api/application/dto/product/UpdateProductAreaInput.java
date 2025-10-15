package accrox.aros.api.application.dto.product;

import accrox.aros.api.application.dto.area.GetAreaInput;

import java.util.Collection;

public record UpdateProductAreaInput (
        String name,
        GetAreaInput area
) {
}
