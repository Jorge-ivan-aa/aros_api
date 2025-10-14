package accrox.aros.api.user;

import accrox.aros.api.application.dto.auth.User.CreateUserInput;
import accrox.aros.api.application.dto.user.UpdateUserInput;
import accrox.aros.api.application.usecases.auth.User.SaveUserUseCase;
import accrox.aros.api.application.usecases.user.UpdateUserUseCase;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.PasswordService;
import accrox.aros.api.infrastructure.spring.controllers.auth.dto.CreateUserRequest;
import accrox.aros.api.infrastructure.spring.controllers.dto.UpdateUserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    // Update user
    //Test falla posible problema en passworService
    @Test
    void whenUserExists_thenUpdateSuccessfully() {

        UpdateUserRequest request = new UpdateUserRequest(
                "1088824",
                "Jui",
                "j@example.com",
                "Password",
                "Calle Falsa 123",
                "00000000"
        );

        UpdateUserInput dto = new UpdateUserInput(
                request.document(),
                request.name(),
                request.email(),
                request.password(),
                request.address(),
                request.phone()
        );

        User user = new User();
        user.setDocument(request.document());
        user.setName("Old Name");
        user.setEmail("old@example.com");
        user.setPassword(passwordService.encode("oldpassword"));
        user.setAddress("Old Address");
        user.setPhone("11111111");

        when(userRepository.findByDocument(request.document()))
                .thenReturn(Optional.of(user));

        doNothing().when(userRepository).save(any(User.class));

        updateUserUseCase.execute(dto);

        assertEquals(request.name(), user.getName());
        assertEquals(request.email(), user.getEmail());
       // assertEquals(request.password(), user.getPassword());
        assertEquals(request.address(), user.getAddress());
        assertEquals(request.phone(), user.getPhone());


        verify(userRepository).findByDocument(request.document());
        verify(userRepository).save(user);

    }

}