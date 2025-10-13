package accrox.aros.api.infrastructure.spring.controllers.dto;

import accrox.aros.api.application.dto.user.DeleteUserInput;
import jakarta.validation.constraints.NotBlank;

public record DeleteUserRequest(
        @NotBlank(message = "Document is required")
        String document
){
        /**
         * transform request into a valid input
         *
         * @return input
         */
        public DeleteUserInput toInput() {
                return new DeleteUserInput(
                        this.document
                );
        }
}
