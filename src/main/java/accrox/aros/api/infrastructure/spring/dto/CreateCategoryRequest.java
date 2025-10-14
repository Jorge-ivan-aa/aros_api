package accrox.aros.api.infrastructure.spring.dto;

import accrox.aros.api.application.dto.category.CreateCategoryInput;
import jakarta.validation.constraints.NotNull;

public class CreateCategoryRequest {
    @NotNull
    private String name;

    public CreateCategoryRequest() {
    }

    public CreateCategoryRequest(String name) {
        this.name = name;
    }

    public CreateCategoryInput toInput() {
        return new CreateCategoryInput(this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
