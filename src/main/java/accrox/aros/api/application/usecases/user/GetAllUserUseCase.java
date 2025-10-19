package accrox.aros.api.application.usecases.user;

import accrox.aros.api.application.dto.area.GetAreaOuput;
import accrox.aros.api.application.dto.user.GetUserOuput;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;


import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class GetAllUserUseCase {

    private final UserRepository userRepository;
    public GetAllUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<GetUserOuput> execute() {
        List<User> users = userRepository.findAll();

        if (users == null || users.isEmpty()) {
            return Collections.emptyList();
        }

        return users.stream()
                .map(user -> new GetUserOuput(
                        user.getName(),
                        user.getDocument(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getAddress(),
                        convertAreas(user.getAreas())
                ))
                .toList();
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
