package accrox.aros.api.infrastructure.spring.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

        @Bean
        public WebMvcConfigurer corsConfigurer() {
                return new WebMvcConfigurer() {
                        @Override
                        public void addCorsMappings(@NonNull CorsRegistry registry) {
                                registry
                                                .addMapping("/**")
                                                .allowedOrigins(
                                                                "http://localhost:4200",
                                                                "https://localhost:4200",
                                                                "http://127.0.0.1:4200")
                                                .allowedMethods(
                                                                "GET",
                                                                "POST",
                                                                "PUT",
                                                                "DELETE",
                                                                "OPTIONS",
                                                                "PATCH",
                                                                "HEAD")
                                                .allowedHeaders("*")
                                                .exposedHeaders(
                                                                "Authorization",
                                                                "Content-Type",
                                                                "X-Requested-With") // ✅ Headers expuestos
                                                .allowCredentials(true)
                                                .maxAge(3600);
                        }
                };
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(
                                Arrays.asList(
                                                "http://localhost:4200",
                                                "https://localhost:4200",
                                                "http://127.0.0.1:4200"));
                configuration.setAllowedMethods(
                                Arrays.asList(
                                                "GET",
                                                "POST",
                                                "PUT",
                                                "DELETE",
                                                "OPTIONS",
                                                "PATCH",
                                                "HEAD"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                configuration.setExposedHeaders(
                                Arrays.asList("Authorization", "Content-Type"));
                configuration.setAllowCredentials(true);
                configuration.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
