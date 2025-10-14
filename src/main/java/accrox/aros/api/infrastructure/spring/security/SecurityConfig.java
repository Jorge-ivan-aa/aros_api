package accrox.aros.api.infrastructure.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Desactiva CSRF para permitir peticiones sin token CSRF
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permite todas las peticiones sin autenticación
            )
            .httpBasic().disable() // Desactiva autenticación básica
            .formLogin().disable(); // Desactiva formulario de login

        return http.build();
    }
}
