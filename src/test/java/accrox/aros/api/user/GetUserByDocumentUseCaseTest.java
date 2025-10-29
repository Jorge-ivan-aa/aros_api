package accrox.aros.api.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import accrox.aros.api.infrastructure.spring.jpa.entity.UserEntity;
import accrox.aros.api.infrastructure.spring.jpa.repository.UserRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import accrox.aros.api.application.dto.user.GetUserByDocumentInput;
import accrox.aros.api.application.dto.user.GetUserOuput;
import accrox.aros.api.application.usecases.user.GetUserByDocumentUseCase;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
public class GetUserByDocumentUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserByDocumentUseCase getUserByDocumentUseCase;

    @Test
    void whenUserExist_thenGetUserByDocument() {

        GetUserByDocumentInput input = new GetUserByDocumentInput("1088824");

        User user1 = new User();
        user1.setName("Nikoll");
        user1.setDocument("1088824");
        user1.setPassword("123");
        user1.setEmail("nkk@mail.com");
        user1.setPhone("30077733303");
        user1.setAddress("Calle 0x");

        when(userRepository.findByDocument(input.document()))
                .thenReturn(Optional.of(user1));

        GetUserOuput result = getUserByDocumentUseCase.execute(input.document());

        assertNotNull(result);
        assertEquals("Nikoll", result.name());
        assertEquals("1088824", result.document());
        assertEquals("nkk@mail.com", result.email());
        verify(userRepository, times(1)).findByDocument("1088824");

    }

}
