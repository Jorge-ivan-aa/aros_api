package accrox.aros.api.application.usecases.user;

import accrox.aros.api.application.dto.user.UpdateUserAreaInput;
import accrox.aros.api.domain.repository.AreaRepository;
import accrox.aros.api.domain.repository.UserRepository;
import jakarta.validation.ValidationException;

public class UpdateUserAreaUseCase {

    private final UserRepository userRepository;
    private final AreaRepository areaRepository;

    public UpdateUserAreaUseCase(UserRepository userRepository, AreaRepository areaRepository) {
        this.userRepository = userRepository;
        this.areaRepository = areaRepository;
    }
    public void execute(UpdateUserAreaInput input) {

        if (input.document() == null || input.document().isBlank()) {
            throw new IllegalArgumentException("The document is required");
        }

        var userOpt = userRepository.findByDocument(input.document());
        if (userOpt.isEmpty()) {
            throw new ValidationException("No user found with the provided document");
        }

        var user = userOpt.get();

        var areas = input.areas().stream()
                .map(areaInput -> areaRepository.getAreaByName(areaInput.name())
                        .orElseThrow(() -> new ValidationException("Area not found: " + areaInput.name())))
                .toList();


        user.setAreas(areas);

        userRepository.save(user);

    }

}
