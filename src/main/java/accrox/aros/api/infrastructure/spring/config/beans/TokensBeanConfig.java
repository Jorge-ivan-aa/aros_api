package accrox.aros.api.infrastructure.spring.config.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import accrox.aros.api.domain.service.TokenService;
import accrox.aros.api.infrastructure.jwt.JwtService;

@Configuration
public class TokensBeanConfig {
    @Bean
    public TokenService tokenService(
            @Value("${security.token.secret}") String accessTokenSecret) {
        return new JwtService(accessTokenSecret);
    }
}