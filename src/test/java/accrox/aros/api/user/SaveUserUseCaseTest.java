package accrox.aros.api.user;

import accrox.aros.api.application.dto.user.CreateUserInput;
import accrox.aros.api.application.usecases.user.SaveUserUseCase;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.PasswordService;
import accrox.aros.api.infrastructure.spring.dto.CreateUserRequest;

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

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private SaveUserUseCase saveUserUseCase;

    @Test
    void whenValidDTO_thenSavesUserSuccessfully() {

        CreateUserInput dto = new CreateUserInput(
                "Carlos Pérez",
                "12345678",
                "carlos@example.com",
                "StrongPassword1",
                "Calle Falsa 123",
                "5551234567");

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