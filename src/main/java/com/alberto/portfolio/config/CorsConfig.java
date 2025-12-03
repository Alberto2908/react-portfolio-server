package com.alberto.portfolio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Static uploads (icons/images)
        registry.addMapping("/uploads/**")
                .allowedOrigins(
                        "http://localhost:5173",
                        "https://alberto-cabello-portfolio.vercel.app",
                        "https://alberto-cabello-portfolio-alberto2908s-projects.vercel.app"
                )
                .allowedMethods("GET", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);

        // API (redundant with @CrossOrigin on controllers, but kept for consistency)
        registry.addMapping("/api/**")
                .allowedOrigins(
                        "http://localhost:5173",
                        "https://alberto-cabello-portfolio.vercel.app",
                        "https://alberto-cabello-portfolio-alberto2908s-projects.vercel.app"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
