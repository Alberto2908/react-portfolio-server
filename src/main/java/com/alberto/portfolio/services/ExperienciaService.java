package com.alberto.portfolio.services;

import com.alberto.portfolio.models.Experiencia;

import java.util.List;
import java.util.Optional;

public interface ExperienciaService {

    List<Experiencia> findAll();

    Optional<Experiencia> findById(String id);

    Experiencia create(Experiencia experiencia);

    Experiencia update(String id, Experiencia experiencia);

    void deleteById(String id);
}
