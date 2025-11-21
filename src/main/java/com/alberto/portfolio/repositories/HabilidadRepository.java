package com.alberto.portfolio.repositories;

import com.alberto.portfolio.models.Habilidad;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HabilidadRepository extends MongoRepository<Habilidad, String> {
}
