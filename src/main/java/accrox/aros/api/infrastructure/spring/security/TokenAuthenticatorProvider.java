package accrox.aros.api.infrastructure.spring.security;

import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.service.TokenService;
import accrox.aros.api.infrastructure.spring.adapters.UserAdminAdapter;
import accrox.aros.api.infrastructure.spring.adapters.UserJpaAdapter;
import accrox.aros.api.infrastructure.spring.security.tokens.AuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class TokenAuthenticatorProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(
        TokenAuthenticatorProvider.class
    );

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
        logger.info("Authenticating token for user");

        if (this.tokenService.validateAccessToken(token)) {
            String document = this.tokenService.extractUserDocument(token);
            logger.info("Token validated for document: {}", document);

            if (userAdminAdapter.isAdminCredentials(document)) {
                logger.info(
                    "User {} is admin, creating admin authentication",
                    document
                );
                User adminUser = userAdminAdapter.getUser();
                UserDetailsAdapter adminDetails = new UserDetailsAdapter(
                    adminUser,
                    true
                );
                logger.info(
                    "Admin authorities: {}",
                    adminDetails.getAuthorities()
                );
                return new AuthenticationToken(
                    adminDetails,
                    token,
                    adminDetails.getAuthorities()
                );
            }

            User user = userJpaAdapter
                .findByDocument(document)
                .orElseThrow(() ->
                    new BadCredentialsException("User not found")
                );

            logger.info(
                "User {} found, creating regular user authentication",
                document
            );
            UserDetailsAdapter userDetails = new UserDetailsAdapter(
                user,
                false // Regular users are not admin
            );
            logger.info("User authorities: {}", userDetails.getAuthorities());
            return new AuthenticationToken(
                userDetails,
                token,
                userDetails.getAuthorities()
            );
        } else {
            logger.info("Token validation failed");
            throw new BadCredentialsException("Invalid or expired token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
