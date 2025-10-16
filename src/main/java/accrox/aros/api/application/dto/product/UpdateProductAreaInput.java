package accrox.aros.api.application.dto.product;

import accrox.aros.api.application.dto.area.GetAreaInput;

public record UpdateProductAreaInput(
                String name,
                GetAreaInput area) {
}