package com.alberto.portfolio.repositories;

import com.alberto.portfolio.models.Formacion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FormacionRepository extends MongoRepository<Formacion, String> {
}
