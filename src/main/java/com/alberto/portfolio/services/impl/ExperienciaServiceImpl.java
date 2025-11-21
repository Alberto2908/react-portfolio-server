package com.alberto.portfolio.services.impl;

import com.alberto.portfolio.models.Experiencia;
import com.alberto.portfolio.repositories.ExperienciaRepository;
import com.alberto.portfolio.services.ExperienciaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExperienciaServiceImpl implements ExperienciaService {

    private final ExperienciaRepository experienciaRepository;

    public ExperienciaServiceImpl(ExperienciaRepository experienciaRepository) {
        this.experienciaRepository = experienciaRepository;
    }

    @Override
    public List<Experiencia> findAll() {
        return experienciaRepository.findAll();
    }

    @Override
    public Optional<Experiencia> findById(String id) {
        return experienciaRepository.findById(id);
    }

    @Override
    public Experiencia create(Experiencia experiencia) {
        return experienciaRepository.save(experiencia);
    }

    @Override
    public Experiencia update(String id, Experiencia experiencia) {
        experiencia.setId(id);
        return experienciaRepository.save(experiencia);
    }

    @Override
    public void deleteById(String id) {
        experienciaRepository.deleteById(id);
    }
}
