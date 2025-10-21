package accrox.aros.api.infrastructure.spring.filters;

import accrox.aros.api.infrastructure.spring.security.tokens.AuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

public class TokenFilter extends OncePerRequestFilter {

    private static final Set<String> PERMITTED_ENDPOINTS = Set.of(
        "/api/login",
        "/api/refresh"
    );

    private AuthenticationProvider authentication;
    private HandlerExceptionResolver exceptionResolver;

    public TokenFilter(
        AuthenticationProvider provider,
        HandlerExceptionResolver exceptionResolver
    ) {
        this.authentication = provider;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // Skip authentication for permitted endpoints
        if (PERMITTED_ENDPOINTS.contains(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                Authentication auth = authentication.authenticate(
                    new AuthenticationToken(token)
                );
                SecurityContext context =
                    SecurityContextHolder.createEmptyContext();
                context.setAuthentication(auth);
                SecurityContextHolder.setContext(context);
            } catch (AuthenticationException ex) {
                SecurityContextHolder.clearContext();
                exceptionResolver.resolveException(request, response, null, ex);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
