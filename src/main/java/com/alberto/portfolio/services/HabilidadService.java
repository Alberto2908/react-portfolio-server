package com.alberto.portfolio.services;

import com.alberto.portfolio.models.Habilidad;

import java.util.List;
import java.util.Optional;

public interface HabilidadService {

    List<Habilidad> findAll();

    Optional<Habilidad> findById(String id);

    Habilidad create(Habilidad habilidad);

    Habilidad update(String id, Habilidad habilidad);

    void deleteById(String id);
}
