package accrox.aros.api.user;

import accrox.aros.api.infrastructure.spring.jpa.entity.UserEntity;
import accrox.aros.api.infrastructure.spring.jpa.repository.UserRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryJpaTest {

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Test
    void whenUserExists_thenFindByDocumentReturnsUser() {
        // Crear y guardar un usuario de prueba
        UserEntity user = new UserEntity();
        user.setName("Nikoll");
        user.setDocument("1088824");
        user.setEmail("nkk@mail.com");
        user.setPassword("123");
        user.setPhone("30077733303");
        user.setAddress("Calle 0x");

        userRepositoryJpa.save(user);

        // Ejecutar el método a probar
        Optional<UserEntity> result = userRepositoryJpa.findByDocument("1088824");
        assertTrue(result.isPresent());
        assertEquals("Nikoll", result.get().getName());
        assertEquals("nkk@mail.com", result.get().getEmail());
    }

    @Test
    void whenUserDoesNotExist_thenFindByDocumentReturnsEmpty() {
        Optional<UserEntity> result = userRepositoryJpa.findByDocument("0000000");
        assertTrue(result.isEmpty());
    }
}