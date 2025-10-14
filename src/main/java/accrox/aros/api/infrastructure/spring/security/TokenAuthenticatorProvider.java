package accrox.aros.api.infrastructure.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.service.TokenService;
import accrox.aros.api.infrastructure.spring.adapters.UserAdminAdapter;
import accrox.aros.api.infrastructure.spring.adapters.UserJpaAdapter;
import accrox.aros.api.infrastructure.spring.security.tokens.AuthenticationToken;
import io.jsonwebtoken.lang.Collections;

public class TokenAuthenticatorProvider implements AuthenticationProvider {
    @Autowired
    private TokenService service;

    private UserJpaAdapter userJpaAdapter;
    private UserAdminAdapter userAdminAdapter;

    public TokenAuthenticatorProvider() {
    }

    public TokenAuthenticatorProvider(TokenService tokenService) {
        this.service = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();

        if (this.service.validateAccessToken(token)) {
            String email = this.service.extractUserEmail(token);

            if (userAdminAdapter.isAdminCredentials(email)) {
                return new AuthenticationToken(
                        new UserDetailsAdapter(userAdminAdapter.getUser()),
                        token,
                        Collections.emptyList());
            }

            User user = userJpaAdapter.findByEmail(email).get();

            return new AuthenticationToken(
                    new UserDetailsAdapter(user),
                    token,
                    Collections.emptyList());
        } else {
            throw new BadCredentialsException("Invalid or expired token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.isAssignableFrom(authentication);
    }

}
