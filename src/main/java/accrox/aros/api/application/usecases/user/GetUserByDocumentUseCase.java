package accrox.aros.api.application.usecases.user;

import accrox.aros.api.application.dto.area.GetAreaOuput;
import accrox.aros.api.application.dto.user.GetUserByDocumentInput;
import accrox.aros.api.application.dto.user.GetUserOuput;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.AreaRepository;
import accrox.aros.api.domain.repository.UserRepository;
import jakarta.validation.ValidationException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GetUserByDocumentUseCase {

    private final UserRepository userRepository;

    public GetUserByDocumentUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public GetUserOuput execute(GetUserByDocumentInput input) {

        if(input.document() == null || input.document().isBlank()){
            throw new IllegalArgumentException("The document is required");
        }

        Optional<User> userOpt = userRepository.findByDocument(input.document());
        if (userOpt.isEmpty()) {
            throw new ValidationException("No user found with the provided document");
        }

        GetUserOuput ouput = new GetUserOuput(
                userOpt.get().getName(),
                userOpt.get().getDocument(),
                userOpt.get().getEmail(),
                userOpt.get().getPhone(),
                userOpt.get().getAddress(),
                convertAreas(userOpt.get().getAreas())
        );

        return ouput;
    }
    private Collection<GetAreaOuput> convertAreas(Collection<Area> areas){
        if(areas == null){
            return null;
        }
        return areas.stream()
                .map(area -> new GetAreaOuput(area.getName()))
                .toList();
    }

}
