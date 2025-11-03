package accrox.aros.api.application.usecases.user;

import accrox.aros.api.application.dto.user.UpdateUserInput;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.PasswordService;
import jakarta.validation.ValidationException;

import java.util.Optional;

public class UpdateUserUseCase {

    private final UserRepository userRepository;

    private final PasswordService passwordService;

    public UpdateUserUseCase(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public void execute(UpdateUserInput toUptate) {
        if (toUptate.document() == null || toUptate.document().isBlank()) {
            throw new ValidationException("The document is required");
        }

        Optional<User> userOpt = userRepository.findByDocument(toUptate.document());
        if (userOpt.isEmpty()) {
            throw new ValidationException("No user found with the provided document");
        }
        if (toUptate.name() != null && !toUptate.name().isBlank()) {
            userOpt.get().setName(toUptate.name());
        }
        if (toUptate.email() != null && !toUptate.email().isBlank()) {
            userOpt.get().setEmail(toUptate.email());
        }
        if (toUptate.address() != null && !toUptate.address().isBlank()) {
            userOpt.get().setAddress(toUptate.address());
        }
        if (toUptate.phone() != null && !toUptate.phone().isBlank()) {
            userOpt.get().setPhone(toUptate.phone());
        }
        if (toUptate.password() != null && !toUptate.password().isBlank()) {
            userOpt.get().setPassword(this.passwordService.encode(toUptate.password()));
        }
         
        if (toUptate.areas() != null) {
            userOpt.get().setAreas(toUptate.areas().stream().map(a -> {
                return new Area(a, null, null);
            }).toList());
        }

        userRepository.update(userOpt.get());
    }
}
