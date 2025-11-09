package accrox.aros.api.application.dto.daymenu;

import java.time.LocalDate;
import java.util.Collection;

public record CreateDayMenuInput (
    String name,
    Float price,
    LocalDate date,
    Collection<DayMenuCategorySimple> products
) {
    //
}
