package accrox.aros.api.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.repository.AreaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import accrox.aros.api.application.dto.user.CreateUserInput;
import accrox.aros.api.application.usecases.user.SaveUserUseCase;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.PasswordService;

@ExtendWith(MockitoExtension.class)
public class SaveUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AreaRepository areaRepository;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private SaveUserUseCase saveUserUseCase;

    @Test
    void whenValidDTO_thenSavesUserSuccessfully() {

        Area areaEntity = new Area();
        areaEntity.setId(1L);
        areaEntity.setName("Bebidas");

        CreateUserInput dto = new CreateUserInput(
                "Carlos Pérez",
                "12345678",
                "carlos@example.com",
                "StrongPassword1",
                "Calle Falsa 123",
                "5551234567",
                List.of(1L));

        when(userRepository.findByDocument(dto.document())).thenReturn(Optional.empty());
        when(passwordService.encode(dto.password())).thenReturn("encodedPassword123");
        when(areaRepository.getAreaById(1L)).thenReturn(Optional.of(areaEntity));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        saveUserUseCase.execute(dto);

        verify(userRepository, times(1)).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertEquals(dto.document(), savedUser.getDocument());
        assertEquals(dto.name(), savedUser.getName());
        assertEquals(dto.email(), savedUser.getEmail());
        assertEquals("encodedPassword123", savedUser.getPassword());
        assertEquals(dto.address(), savedUser.getAddress());
        assertEquals(dto.phone(), savedUser.getPhone());
        assertEquals(1, savedUser.getAreas().size());
        assertEquals("Bebidas", savedUser.getAreas().iterator().next().getName());

        assertDoesNotThrow(() -> saveUserUseCase.execute(dto));
    }

}