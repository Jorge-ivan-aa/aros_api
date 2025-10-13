package accrox.aros.api.user;

import accrox.aros.api.application.dto.user.DeleteUserInput;
import accrox.aros.api.application.usecases.user.DeleteUserUseCase;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.infrastructure.spring.controllers.dto.DeleteUserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;
    
    //Delete User
    @Test
    void whenUserExists_thenDeletesSuccessfully(){

        DeleteUserRequest request = new DeleteUserRequest("12345678");

        DeleteUserInput input = new DeleteUserInput(request.document());

        User user = new User();
        user.setDocument(request.document());

        when(userRepository.findByDocument(request.document()))
                .thenReturn(Optional.of(user));

        deleteUserUseCase.execute(input);

        verify(userRepository, times(1)).deleteUserByDocument(input.document());
    }

}
