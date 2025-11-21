package com.alberto.portfolio.services;

import com.alberto.portfolio.models.Proyecto;

import java.util.List;
import java.util.Optional;

public interface ProyectoService {

    List<Proyecto> findAll();

    Optional<Proyecto> findById(String id);

    Proyecto create(Proyecto proyecto);

    Proyecto update(String id, Proyecto proyecto);

    void deleteById(String id);
}
