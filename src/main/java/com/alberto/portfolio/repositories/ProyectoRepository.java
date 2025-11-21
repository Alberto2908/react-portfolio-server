package com.alberto.portfolio.repositories;

import com.alberto.portfolio.models.Proyecto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProyectoRepository extends MongoRepository<Proyecto, String> {
}
