package accrox.aros.api.auth;

import accrox.aros.api.application.dto.auth.AuthInput;
import accrox.aros.api.application.usecases.auth.LoginTokenUseCase;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.model.RefreshToken;
import accrox.aros.api.domain.repository.RefreshTokenRepository;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.AdminAuthService;
import accrox.aros.api.domain.service.PasswordService;
import accrox.aros.api.domain.service.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class LoginTokenUseCaseTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdminAuthService adminAuthService;

    @Mock
    private RefreshTokenRepository tokenRepository;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private LoginTokenUseCase useCase = new LoginTokenUseCase(
            tokenService,
            userRepository,
            adminAuthService,
            tokenRepository,
            passwordService,
            0);

    @Test
    void whenCredentialsAreValid_thenReturnTokensAndSaveRefreshToken() throws Exception {
        AuthInput input = new AuthInput("user@example.com", "secret");

        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("hashed");

        when(adminAuthService.isAdminCredentials(input.document())).thenReturn(false);
        when(userRepository.findByEmail(input.document())).thenReturn(java.util.Optional.of(user));
        when(passwordService.matches(input.password(), user.getPassword())).thenReturn(true);
        when(tokenService.generateRefreshToken(user.getEmail())).thenReturn("r-token");
        when(tokenService.generateAccessToken(user.getEmail())).thenReturn("a-token");

        var output = useCase.execute(input);

        assertEquals("r-token", output.refresh());
        assertEquals("a-token", output.access());

        verify(tokenRepository, times(1)).create(any(RefreshToken.class));
    }

}
