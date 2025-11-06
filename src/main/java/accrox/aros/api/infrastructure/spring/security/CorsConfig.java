package accrox.aros.api.infrastructure.spring.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
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
        @Value("${security.origins}")
        private String[] origins;

        @Bean
        public WebMvcConfigurer corsConfigurer() {
                return new WebMvcConfigurer() {
                        @Override
                        public void addCorsMappings(@NonNull CorsRegistry registry) {
                                registry
                                                .addMapping("/**")
                                                .allowedOrigins(origins)
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
                                                                "X-Requested-With")
                                                .allowCredentials(true)
                                                .maxAge(3600);
                        }
                };
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(
                                Arrays.asList(origins));
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
