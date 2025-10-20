package accrox.aros.api.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import accrox.aros.api.application.dto.user.UpdateUserInput;
import accrox.aros.api.application.usecases.user.UpdateUserUseCase;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.PasswordService;

@ExtendWith(MockitoExtension.class)
public class UpdateUserUseCaseTest {

        @Mock
        private UserRepository userRepository;

        @Mock
        private PasswordService passwordService;

        @InjectMocks
        private UpdateUserUseCase updateUserUseCase;


        @Test
        void whenUserExists_thenUpdateSuccessfully() {

                UpdateUserInput dto = new UpdateUserInput(
                        "1088824",
                        "Jui",
                        "j@example.com",
                        "Password",
                        "Calle Falsa 123",
                        "00000000"
                );

                User user = new User();
                user.setDocument("1088824");
                user.setName("Old Name");
                user.setEmail("old@example.com");
                user.setPassword("oldpassword");
                user.setAddress("Old Address");
                user.setPhone("11111111");

                when(userRepository.findByDocument(dto.document()))
                        .thenReturn(Optional.of(user));

                when(passwordService.encode(dto.password()))
                        .thenReturn("encodedPassword");

                doNothing().when(userRepository).update(any(User.class));

                updateUserUseCase.execute(dto);

                assertEquals(dto.name(), user.getName());
                assertEquals(dto.email(), user.getEmail());
                assertEquals("encodedPassword", user.getPassword());
                assertEquals(dto.address(), user.getAddress());
                assertEquals(dto.phone(), user.getPhone());

                verify(userRepository).findByDocument(dto.document());
                verify(userRepository).update(user);
        }

}