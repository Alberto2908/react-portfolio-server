package com.alberto.portfolio.repositories;

import com.alberto.portfolio.models.ContactaConmigo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactaConmigoRepository extends MongoRepository<ContactaConmigo, String> {
}
