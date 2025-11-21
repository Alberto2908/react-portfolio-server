package com.alberto.portfolio.services.impl;

import com.alberto.portfolio.models.Habilidad;
import com.alberto.portfolio.repositories.HabilidadRepository;
import com.alberto.portfolio.services.HabilidadService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabilidadServiceImpl implements HabilidadService {

    private final HabilidadRepository habilidadRepository;

    public HabilidadServiceImpl(HabilidadRepository habilidadRepository) {
        this.habilidadRepository = habilidadRepository;
    }

    @Override
    public List<Habilidad> findAll() {
        return habilidadRepository.findAll();
    }

    @Override
    public Optional<Habilidad> findById(String id) {
        return habilidadRepository.findById(id);
    }

    @Override
    public Habilidad create(Habilidad habilidad) {
        return habilidadRepository.save(habilidad);
    }

    @Override
    public Habilidad update(String id, Habilidad habilidad) {
        habilidad.setId(id);
        return habilidadRepository.save(habilidad);
    }

    @Override
    public void deleteById(String id) {
        habilidadRepository.deleteById(id);
    }
}
