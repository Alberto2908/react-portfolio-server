package com.alberto.portfolio.repositories;

import com.alberto.portfolio.models.VisitStats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VisitStatsRepository extends MongoRepository<VisitStats, String> {
}
