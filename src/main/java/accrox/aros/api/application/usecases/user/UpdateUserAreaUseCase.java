package accrox.aros.api.application.usecases.user;

import accrox.aros.api.application.dto.area.GetAreaInput;
import accrox.aros.api.application.dto.user.UpdateUserAreaInput;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.AreaRepository;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.AreaEntity;
import jakarta.validation.ValidationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class UpdateUserAreaUseCase {

    private final UserRepository userRepository;
    private final AreaRepository areaRepository;

    public UpdateUserAreaUseCase(UserRepository userRepository, AreaRepository areaRepository) {
        this.userRepository = userRepository;
        this.areaRepository = areaRepository;
    }
    public void execute(UpdateUserAreaInput input){

        if(input.document() == null || input.document().isBlank()){
            throw new IllegalArgumentException("The document is required");
        }

        Optional<User> userOpt = userRepository.findByDocument(input.document());
        if (userOpt.isEmpty()) {
            throw new ValidationException("No user found with the provided document");
        }

        User user = userOpt.get();

        Collection<AreaEntity> areas = new ArrayList<>();
        for (GetAreaInput areaDto : input.areas()) {
            Area area = areaRepository.getAreaByName(areaDto.name())
                    .orElseThrow(() -> new ValidationException(
                            "Area not found: " + areaDto.name()));

            AreaEntity areaEntity = new AreaEntity();

            areaEntity.setId(area.getId());
            areaEntity.setName(area.getName());

            areas.add(areaEntity);
        }

        userRepository.updateUserArea(user,areas);
    }

}
