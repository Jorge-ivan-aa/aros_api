package accrox.aros.api.application.usecases.user;

import accrox.aros.api.application.dto.user.CreateUserInput;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.AreaRepository;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.PasswordService;
import jakarta.validation.ValidationException;

public class SaveUserUseCase {

    private final UserRepository userRepository;

    private final AreaRepository areaRepository;

    private final PasswordService passwordService;

    public SaveUserUseCase(UserRepository userRepository,AreaRepository areaRepository ,PasswordService passwordService) {
        this.userRepository = userRepository;
        this.areaRepository = areaRepository;
        this.passwordService = passwordService;
    }

    public void execute(CreateUserInput dto) throws ValidationException {

        if (dto.document() != null && userRepository.findByDocument(dto.document()).isPresent()) {
            throw new ValidationException("A user with the provided document already exists");
        }

        if (dto.name().isBlank()) {
            throw new ValidationException("Name is required");
        }

        User user = new User();
        user.setDocument(dto.document());
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(this.passwordService.encode(dto.password()));
        user.setAddress(dto.address());
        user.setPhone(dto.phone());

        var areas = dto.areas().stream()
                .map(areaInput -> areaRepository.getAreaByName(areaInput.name())
                        .orElseThrow(() -> new ValidationException("Area not found: " + areaInput.name())))
                .toList();
        user.setAreas(areas);

        userRepository.save(user);
    }
}