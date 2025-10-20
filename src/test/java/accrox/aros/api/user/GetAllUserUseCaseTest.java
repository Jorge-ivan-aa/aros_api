package accrox.aros.api.user;

import accrox.aros.api.application.dto.user.GetUserOuput;
import accrox.aros.api.application.usecases.user.GetAllUserUseCase;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAllUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetAllUserUseCase getAllUserUseCase;

    @Test
    void whenUsersExist_thenGetAllUsers (){

        User user1 = new User();
        user1.setName("Juan");
        user1.setPassword("123");
        user1.setEmail("juan@mail.com");
        user1.setPhone("3001112233");
        user1.setAddress("Calle 10");

        User user2 = new User();
        user2.setName("Ana");
        user2.setPassword("456");
        user2.setEmail("ana@mail.com");
        user2.setPhone("3004445566");
        user2.setAddress("Calle 20");

        List<User> mockUsers = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<GetUserOuput> result = getAllUserUseCase.execute();


        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Juan", result.get(0).name());
        assertEquals("Ana", result.get(1).name());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void whenNoUsersExist_thenReturnEmptyList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<GetUserOuput> result = getAllUserUseCase.execute();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

}
