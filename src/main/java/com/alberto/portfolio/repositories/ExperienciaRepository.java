package com.alberto.portfolio.repositories;

import com.alberto.portfolio.models.Experiencia;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExperienciaRepository extends MongoRepository<Experiencia, String> {
}
