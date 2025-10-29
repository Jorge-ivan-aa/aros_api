package accrox.aros.api.application.usecases.user;

import accrox.aros.api.application.dto.user.DeleteUserInput;
import accrox.aros.api.domain.repository.UserRepository;
import jakarta.validation.ValidationException;

public class DeleteUserUseCase {

    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void execute(String document) {
        if (document== null || document.isBlank()) {
            throw new ValidationException("The document is required");
        }

        var userOpt = userRepository.findByDocument(document);
        if (userOpt.isEmpty()) {
            throw new ValidationException("No user found with the provided document");
        }

        userRepository.deleteUserByDocument(document);
    }
}