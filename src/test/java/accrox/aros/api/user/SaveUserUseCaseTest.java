package accrox.aros.api.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import accrox.aros.api.application.dto.area.GetAreaInput;
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
    private PasswordService passwordService;

    @InjectMocks
    private SaveUserUseCase saveUserUseCase;

    @Test
    void whenValidDTO_thenSavesUserSuccessfully() {
        GetAreaInput area = new GetAreaInput("Cocina");
        Collection<GetAreaInput> areas = List.of(area);
        
        CreateUserInput dto = new CreateUserInput(
                "Carlos Pérez",
                "12345678",
                "carlos@example.com",
                "StrongPassword1",
                "Calle Falsa 123",
                "5551234567",
                areas
        );

        when(userRepository.findByDocument(dto.document())).thenReturn(Optional.empty());

        when(passwordService.encode(dto.password())).thenReturn("encodedPassword123");

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

        assertDoesNotThrow(() -> saveUserUseCase.execute(dto));
    }

}