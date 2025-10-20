package accrox.aros.api.user;

import accrox.aros.api.application.dto.area.GetAreaInput;
import accrox.aros.api.application.dto.user.UpdateUserAreaInput;
import accrox.aros.api.application.usecases.user.UpdateUserAreaUseCase;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.AreaRepository;
import accrox.aros.api.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateUserAreaUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AreaRepository areaRepository;

    @InjectMocks
    private UpdateUserAreaUseCase updateUserAreaUseCase;

    @Test
    void whenUserExists_thenUpdateAreaSuccessfully() {

        User user1 = new User();
        user1.setName("Ana");
        user1.setDocument("123456789");
        user1.setPassword("456");
        user1.setEmail("ana@mail.com");
        user1.setPhone("3004445566");
        user1.setAddress("Calle 20");

        GetAreaInput area1 = new GetAreaInput("Área1");
        GetAreaInput area2 = new GetAreaInput("Área2");

        UpdateUserAreaInput updateUserAreas = new UpdateUserAreaInput(
                "123456789",
                List.of(area1, area2)
        );

        Area areaEntity1 = new Area();
        areaEntity1.setName("Área1");

        Area areaEntity2 = new Area();
        areaEntity2.setName("Área2");

        when(areaRepository.getAreaByName("Área1")).thenReturn(Optional.of(areaEntity1));
        when(areaRepository.getAreaByName("Área2")).thenReturn(Optional.of(areaEntity2));

        when(userRepository.findByDocument(updateUserAreas.document()))
                .thenReturn(Optional.of(user1));

        updateUserAreaUseCase.execute(updateUserAreas);

        assert(user1.getAreas().size() == 2);
        assert(user1.getAreas().stream().anyMatch(a -> a.getName().equals("Área1")));
        assert(user1.getAreas().stream().anyMatch(a -> a.getName().equals("Área2")));

        verify(userRepository).findByDocument("123456789");
        verify(areaRepository).getAreaByName("Área1");
        verify(areaRepository).getAreaByName("Área2");

    }

}
