package accrox.aros.api.infrastructure.spring.security;

import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.service.TokenService;
import accrox.aros.api.infrastructure.spring.adapters.UserAdminAdapter;
import accrox.aros.api.infrastructure.spring.adapters.UserJpaAdapter;
import accrox.aros.api.infrastructure.spring.security.tokens.AuthenticationToken;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class TokenAuthenticatorProvider implements AuthenticationProvider {

    private final TokenService tokenService;
    private final UserJpaAdapter userJpaAdapter;
    private final UserAdminAdapter userAdminAdapter;

    @Autowired
    public TokenAuthenticatorProvider(
        TokenService tokenService,
        UserJpaAdapter userJpaAdapter,
        UserAdminAdapter userAdminAdapter
    ) {
        this.tokenService = tokenService;
        this.userJpaAdapter = userJpaAdapter;
        this.userAdminAdapter = userAdminAdapter;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        String token = (String) authentication.getCredentials();

        if (this.tokenService.validateAccessToken(token)) {
            String email = this.tokenService.extractUserEmail(token);

            if (userAdminAdapter.isAdminCredentials(email)) {
                return new AuthenticationToken(
                    new UserDetailsAdapter(userAdminAdapter.getUser()),
                    token,
                    Collections.emptyList()
                );
            }

            User user = userJpaAdapter
                .findByEmail(email)
                .orElseThrow(() ->
                    new BadCredentialsException("User not found")
                );

            return new AuthenticationToken(
                new UserDetailsAdapter(user),
                token,
                Collections.emptyList()
            );
        } else {
            throw new BadCredentialsException("Invalid or expired token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
