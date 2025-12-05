package com.alberto.portfolio.config;

import com.alberto.portfolio.services.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.http.HttpStatus;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
            )
            .authorizeHttpRequests(auth -> auth
                // Static uploads (icons/images)
                .requestMatchers("/uploads/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll()

                // Public auth and utilities
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/health").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/visits/increment").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/visits").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/contact/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/contacto/**").permitAll()

                // Public reads for content
                .requestMatchers(HttpMethod.GET, "/api/experiencias/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/formaciones/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/habilidades/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/proyectos/**").permitAll()

                // Admin: write operations (non-GET) on content
                .requestMatchers(HttpMethod.POST, "/api/experiencias/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/experiencias/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/experiencias/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/formaciones/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/formaciones/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/formaciones/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/habilidades/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/habilidades/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/habilidades/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/proyectos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/proyectos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/proyectos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/cv/**").hasRole("ADMIN")

                .anyRequest().permitAll()
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList(
            "http://localhost:5173",
            "https://*.vercel.app",
            "https://*.onrender.com"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
