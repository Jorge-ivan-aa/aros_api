package accrox.aros.api.user;

import accrox.aros.api.application.dto.auth.User.CreateUserInput;
import accrox.aros.api.application.usecases.auth.User.SaveUserUseCase;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.infrastructure.spring.controllers.auth.dto.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaveUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SaveUserUseCase saveUserUseCase;

    // Saved user
    @Test
    void whenValidDTO_thenSavesUserSuccessfully() {

        CreateUserRequest request = new CreateUserRequest(
                "Carlos Pérez",
                "12345678",
                "carlos@example.com",
                "StrongPassword1",
                "Calle Falsa 123",
                "5551234567"
        );

        CreateUserInput dto = new CreateUserInput(
                request.name(),
                request.document(),
                request.email(),
                request.password(),
                request.address(),
                request.phone()
        );

        when(userRepository.findByDocument(dto.document())).thenReturn(Optional.empty());

        User user = new User();
        user.setId(1L);
        user.setDocument(dto.document());
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setAddress(dto.address());
        user.setPhone(dto.phone());

        doNothing().when(userRepository).save(any(User.class));

        saveUserUseCase.execute(dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertEquals(dto.document(), savedUser.getDocument());
        assertEquals(dto.name(), savedUser.getName());
        assertEquals(dto.email(), savedUser.getEmail());
        assertEquals(dto.password(), savedUser.getPassword());
        assertEquals(dto.address(), savedUser.getAddress());
        assertEquals(dto.phone(), savedUser.getPhone());

        assertDoesNotThrow(() -> saveUserUseCase.execute(dto));
    }

}