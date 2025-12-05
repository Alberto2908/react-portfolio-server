package com.alberto.portfolio.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UploadsInitializer implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String baseDir = System.getenv().getOrDefault("UPLOADS_DIR", "uploads");
        Path base = Paths.get(baseDir);
        Path habilidades = base.resolve("habilidades");
        Path proyectos = base.resolve("proyectos");
        Files.createDirectories(habilidades);
        Files.createDirectories(proyectos);
    }
}
