package com.alberto.portfolio.services;

import com.alberto.portfolio.models.Formacion;

import java.util.List;
import java.util.Optional;

public interface FormacionService {

    List<Formacion> findAll();

    Optional<Formacion> findById(String id);

    Formacion create(Formacion formacion);

    Formacion update(String id, Formacion formacion);

    void deleteById(String id);
}
