package com.alberto.portfolio.services.impl;

import com.alberto.portfolio.models.VisitStats;
import com.alberto.portfolio.repositories.VisitStatsRepository;
import com.alberto.portfolio.services.VisitStatsService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class VisitStatsServiceImpl implements VisitStatsService {

    private static final String VISITS_ID = "VISITS";

    private final VisitStatsRepository repo;

    public VisitStatsServiceImpl(VisitStatsRepository repo) {
        this.repo = repo;
    }

    @Override
    public VisitStats get() {
        return repo.findById(VISITS_ID).orElseGet(() -> {
            VisitStats v = new VisitStats(VISITS_ID, 0L, null, Instant.now());
            return repo.save(v);
        });
    }

    @Override
    public synchronized VisitStats increment() {
        VisitStats v = repo.findById(VISITS_ID).orElseGet(() -> new VisitStats(VISITS_ID, 0L, null, Instant.now()));
        v.setTotal(v.getTotal() + 1);
        v.setLastVisitAt(Instant.now());
        if (v.getCreatedAt() == null) v.setCreatedAt(Instant.now());
        return repo.save(v);
    }
}
