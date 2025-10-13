package accrox.aros.api.application.usecases.auth.User;

import accrox.aros.api.application.dto.auth.User.CreateUserInput;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;
import jakarta.validation.ValidationException;

public class SaveUserUseCase {

    private final UserRepository userRepository;

    public SaveUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(CreateUserInput dto) throws ValidationException {

        if (dto.document() != null && userRepository.findByDocument(dto.document()).isPresent()) {
            throw new ValidationException("A user with the provided document already exists");
        }

        if(dto.name().isBlank()){
            throw new ValidationException("Name is required");
        }

        User user = new User();
        user.setDocument(dto.document());
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setAddress(dto.address());
        user.setPhone(dto.phone());

        userRepository.save(user);
    }
}