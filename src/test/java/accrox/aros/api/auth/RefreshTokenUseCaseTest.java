package accrox.aros.api.auth;

import accrox.aros.api.application.dto.auth.AuthOutput;
import accrox.aros.api.application.usecases.auth.RefreshTokenUseCase;
import accrox.aros.api.domain.model.RefreshToken;
import accrox.aros.api.domain.repository.RefreshTokenRepository;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.TokenService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenUseCaseTest {

    @Mock
    private RefreshTokenRepository tokenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private EntityManager manager;

    @InjectMocks
    private RefreshTokenUseCase useCase;

    @Test
    void whenValidRefreshToken_thenIssueNewTokens() throws Exception {
        RefreshToken stored = new RefreshToken(
                1L,
                "old-hash",
                null,
                "user@example.com");

        when(tokenRepository.findByHash("old-hash")).thenReturn(java.util.Optional.of(stored));
        when(tokenService.generateRefreshToken(stored.getUserEmail())).thenReturn("new-refresh");
        when(tokenService.generateAccessToken(stored.getUserEmail())).thenReturn("new-access");

        AuthOutput out = useCase.execute("old-hash");

        assertEquals("new-refresh", out.refresh());
        assertEquals("new-access", out.access());

        verify(manager, times(1)).clear();
        verify(tokenRepository, times(1)).revokeToken(stored.getId());
        verify(tokenRepository, times(1)).create(any(RefreshToken.class));
    }

}
