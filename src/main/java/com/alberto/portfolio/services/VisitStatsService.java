package com.alberto.portfolio.services;

import com.alberto.portfolio.models.VisitStats;

public interface VisitStatsService {
    VisitStats get();
    VisitStats increment();
}
